package ru.cb.app.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component("fileMaganerCamelContext")
public class FileMaganerCamelContext extends DefaultCamelContext {

	public FileMaganerCamelContext(@Qualifier("jndiContext") JndiContext jndiContext,
			@Qualifier("routeBuilder") RouteBuilder routeBuilder) throws Exception {
		super(jndiContext);
		this.addRoutes(routeBuilder);
	}

}
