package ru.cb.app.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("changeFileNameProcessor")
public class ChangeFileNameProcessor implements Processor {

	private static final Logger logger = LoggerFactory.getLogger(ChangeFileNameProcessor.class);

	@Override
	public void process(Exchange exchange) {
		try {
			final String dateString = new SimpleDateFormat("ddMMyy_hhmm").format(new Date());
			final String fileName = String.join("", "json_", dateString, ".txt");
			final String flagFileName = String.join("", "<json_", dateString, ">.ready");

			exchange.getIn().setHeader("fileName", fileName);
			exchange.getIn().setHeader("flagFileName", flagFileName);
			logger.info("changeFileNameProcessor finished");
		} catch (Exception e) {
			logger.error("process error: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
