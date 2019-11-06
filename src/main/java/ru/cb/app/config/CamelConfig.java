package ru.cb.app.config;

import java.util.Hashtable;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;
import ru.cb.app.mapper.PersonJsonMapper;

@Configuration
@ConfigurationProperties("camel-route")
@Setter
public class CamelConfig {

    private String preparePath;
    private String workPath;
    private String dataPath;

    @Bean
    public JndiContext jndiContext(PersonJsonMapper personJsonMapper,
            @Qualifier("changeFileNameProcessor") Processor changeFileNameProcessor,
            @Qualifier("readyFileProcessor") Processor readyFileProcessor,
            @Qualifier("extendedDataProcessor") Processor extendedDataProcessor) throws Exception {
        final JndiContext jndiContext = new JndiContext(new Hashtable<String, Object>());
        jndiContext.bind("personJsonMapper", personJsonMapper);
        jndiContext.bind("changeFileNameProcessor", changeFileNameProcessor);
        jndiContext.bind("readyFileProcessor", readyFileProcessor);
        jndiContext.bind("extendedDataProcessor", extendedDataProcessor);
        return jndiContext;
    }

    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {

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
        };
    }

    @Bean
    public CamelContext camelContext(JndiContext jndiContext, RouteBuilder routeBuilder) throws Exception {
        final DefaultCamelContext camelContext = new DefaultCamelContext(jndiContext);
        camelContext.addRoutes(routeBuilder);
        camelContext.start();
        return camelContext;
    }

    @Bean
    public ProducerTemplate producerTemplate(CamelContext camelContext) {
        return camelContext.createProducerTemplate();
    }

}