package ru.cb.app.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("routeBuilder")
@ConfigurationProperties("camel-route")
public class FileManagerRouteBuilder extends RouteBuilder {

	private String preparePath;
	private String workPath;
	private String dataPath;

	public void setPreparePath(String preparePath) {
		this.preparePath = preparePath;
	}

	public void setWorkPath(String workPath) {
		this.workPath = workPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	public void configure() throws Exception {
		from("direct:prepare").to("bean:personJsonMapper?method=personToJsonString")
				.to("file:prepare?fileName=${header.fileName}");

		from("direct:deletejson").pollEnrich("file:" + preparePath + "?fileName=${header.fileName}");

		from("direct:work").pollEnrich("file:" + preparePath + "?fileName=${header.fileName}")
				.process("changeFileNameProcessor").to("file:" + workPath + "?fileName=${header.fileName}")
				.setBody(constant("")).to("file:" + workPath + "?fileName=${header.flagFileName}");

		from("timer:automover?period=10s").pollEnrich("file:" + workPath + "?include=.*ready$")
				.process("readyFileProcessor").pollEnrich()
				.simple("file:" + workPath + "?delete=true&fileName=${header.fileName}")
				.process("extendedDataProcessor").to("file:" + dataPath);
	}

}
