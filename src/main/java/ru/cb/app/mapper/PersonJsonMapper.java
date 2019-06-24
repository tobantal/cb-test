package ru.cb.app.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.cb.app.domain.Person;

@SuppressWarnings("serial")
@Component
public class PersonJsonMapper extends ObjectMapper {

	private static final Logger logger = LoggerFactory.getLogger(PersonJsonMapper.class);
	
	
	public String personToJsonString(Person person) {		
		try {
			String jsonString = writeValueAsString(person);
			logger.info(person + " converted to " + jsonString);
			return jsonString;	
		} catch (JsonProcessingException jpe) {
			logger.error(jpe.getMessage());
			throw new RuntimeException(jpe);
		}	
	}
	
	public Person jsonStringToPerson(String jsonString) {
		Person person;
		try {
			person = readValue(jsonString, Person.class);
			logger.info(jsonString + " converted to " + person);
			return person;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);	
		}
		
	}

}
