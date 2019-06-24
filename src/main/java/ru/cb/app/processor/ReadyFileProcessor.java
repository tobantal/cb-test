package ru.cb.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("readyFileProcessor")
public class ReadyFileProcessor implements Processor {

	private static final Logger logger = LoggerFactory.getLogger(ReadyFileProcessor.class);

	@Override
	public void process(Exchange exchange) {
		try {
		       String flagFileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
		       String fileName = flagFileName.substring(1, flagFileName.length()-7) + ".txt";
		       exchange.getIn().setHeader("fileName", fileName);
		       logger.info("file {} is ready", fileName);
		} catch(Exception e) {
			logger.error("process error: {}", e.getMessage());
			throw new RuntimeException(e);
		}

	}

}
