package ru.cb.app.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component("routeBuilder")
public class FileManagerRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:prepare").to("bean:personJsonMapper?method=personToJsonString")
				.to("file:prepare?fileName=${header.fileName}");

		from("direct:deletejson").pollEnrich("file:prepare?fileName=${header.fileName}");

		from("direct:work").pollEnrich("file:prepare?fileName=${header.fileName}").process("changeFileNameProcessor")
				.to("file:work?fileName=${header.fileName}").setBody(constant(""))
				.to("file:work?fileName=${header.flagFileName}");

		from("timer:automover?period=10s").pollEnrich("file:work?include=.*ready$").process("readyFileProcessor")
				.pollEnrich().simple("file:work?delete=true&fileName=${header.fileName}")
				.process("extendedDataProcessor")
				.to("file:data");
	}

}
