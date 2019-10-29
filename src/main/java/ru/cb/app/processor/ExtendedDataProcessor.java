package ru.cb.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import lombok.RequiredArgsConstructor;
import ru.cb.app.domain.Person;
import ru.cb.app.mapper.PersonJsonMapper;
import ru.cb.app.service.PersonService;
import ru.cb.app.service.WorkService;
import ru.cb.app.validator.PersonValidator;

@Component("extendedDataProcessor")
@RequiredArgsConstructor
public class ExtendedDataProcessor implements Processor {

	private static final Logger logger = LoggerFactory.getLogger(ExtendedDataProcessor.class);

	private final PersonJsonMapper mapper;
	private final WorkService workService;
	private final PersonValidator personValidator;
	private final PersonService personService;

	@Override
	public void process(Exchange exchange) {
        String jsonString = null;
		try {
			jsonString = exchange.getIn().getBody(String.class);
			final Person person = mapper.jsonStringToPerson(jsonString);

			final Errors errors = new BeanPropertyBindingResult(person, "person");
			personValidator.validate(person, errors);
			if (!errors.getAllErrors().isEmpty()) {
				logger.error("Validation Error: {}", jsonString);
				return;
			}

			final String company = workService.findCompanyByPerson(person);
			person.setCompany(company);

			personService.save(person);
			logger.info("{} saved", person);

			jsonString = mapper.personToJsonString(person);
			exchange.getIn().setBody(jsonString);
			logger.info("extendedDataProcessor finished");

		} catch (Exception e) {
			logger.error("process error: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
