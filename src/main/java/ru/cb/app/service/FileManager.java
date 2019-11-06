package ru.cb.app.service;

import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import ru.cb.app.domain.Person;
import ru.cb.app.mapper.DateFileNameMapper;

@Service
public class FileManager implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(FileManager.class);

    private final ProducerTemplate producerTemplate;

    private final DateFileNameMapper dateFileNameMapper;

    public FileManager(DateFileNameMapper dateFileNameMapper, CamelContext camelContext) throws Throwable {
        this.dateFileNameMapper = dateFileNameMapper;
        producerTemplate = camelContext.createProducerTemplate();
        camelContext.start();
    }

    public String createPrepareFile(Person person) {
        final String prepareFile = dateFileNameMapper.dateToFileName(new Date(), "data_", ".json");
        try {
            producerTemplate.sendBodyAndHeader("direct:prepare", person, "fileName", prepareFile);
            logger.info("Prepare file {} created", prepareFile);
        } catch (CamelExecutionException cee) {
            logger.error("CamelExecutionException to create file {}: {}", prepareFile, cee.getMessage());
        }
        return prepareFile;
    }

    public void movePrepareFile(String prepareFile) {
        try {
            producerTemplate.sendBodyAndHeader("direct:work", "", "fileName", prepareFile);
        } catch (CamelExecutionException cee) {
            logger.error("CamelExecutionException to move file {}: {}", prepareFile, cee.getMessage());
        }
    }

    public void deletePrepareFile(String prepareFile) {
        try {
            producerTemplate.sendBodyAndHeader("direct:deletejson", "", "fileName", prepareFile);
        } catch (CamelExecutionException cee) {
            logger.error("CamelExecutionException to delete file {}: {}", prepareFile, cee.getMessage());
        }
    }

    @Override
    public void destroy() throws Exception {
        // producerTemplate.stop();
        // producerTemplate.getCamelContext().stop();
    }

}
