package ru.cb.app.camel;

import java.util.Hashtable;

import org.apache.camel.Processor;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ru.cb.app.mapper.PersonJsonMapper;

@Component("jndiContext")
public class FileManagerJndiContext extends JndiContext {
	
	private static final long serialVersionUID = -4711556601739136214L;

	protected FileManagerJndiContext(PersonJsonMapper personJsonMapper,
			 @Qualifier("changeFileNameProcessor") Processor changeFileNameProcessor,
			 @Qualifier("readyFileProcessor") Processor readyFileProcessor,
			 @Qualifier("extendedDataProcessor") Processor extendedDataProcessor) throws Exception {		
		super(new Hashtable<String, Object>());		
		this.bind("personJsonMapper", personJsonMapper);
		this.bind("changeFileNameProcessor", changeFileNameProcessor);
		this.bind("readyFileProcessor", readyFileProcessor);
		this.bind("extendedDataProcessor", extendedDataProcessor);
	}

}
