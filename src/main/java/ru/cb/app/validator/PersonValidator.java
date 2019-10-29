package ru.cb.app.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.Setter;
import ru.cb.app.domain.Person;

@Component("personValidator")
@ConfigurationProperties("person-validator")
@Setter
public class PersonValidator implements Validator {

	private String mobilePattern;
	private String birthDatePattern;
	private String birthDateFormat;
	private String emailPattern;

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		rejectIfEmptyOrWhitespace(errors);

        final Person person = (Person) target;

        namesValidation(person, errors);

        phonesValidation(person, errors);

        emailValidation(person, errors);

        birthDateValidation(person, errors);
	}

	private void namesValidation(Person person, Errors errors) {
        if(person.getFirstName().length() > 10) {
        	errors.rejectValue("firstName", "error.person.first-name");
        }

        if(person.getLastName().length() > 20) {
        	errors.rejectValue("lastName", "error.person.last-name");
        }
	}

	private void emailValidation(Person person, Errors errors) {
        if(!Pattern.matches(emailPattern, person.getEmail())) {
        	errors.rejectValue("email", "error.person.email");
        }
	}

	private void rejectIfEmptyOrWhitespace(Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.empty-field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.empty-field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobilePhone", "error.empty-field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workPhone", "error.empty-field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.empty-field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "error.empty-field");
	}

	private void phonesValidation(Person person, Errors errors) {
        if(!person.getMobilePhone().startsWith(person.getWorkPhone().substring(0, 3))) {
        	errors.rejectValue("workPhone", "error.person.phone.nnn");
        	errors.rejectValue("mobilePhone", "error.person.phone.nnn");
        }

        if(!Pattern.matches(mobilePattern, person.getMobilePhone())) {
        	errors.rejectValue("mobilePhone", "error.person.phone.format");
        }

        if(!Pattern.matches(mobilePattern, person.getWorkPhone())) {
        	errors.rejectValue("workPhone", "error.person.phone.format");
        }
	}

	private void birthDateValidation(Person person, Errors errors) {
        if(!Pattern.matches(birthDatePattern, person.getBirthDate())) {
        	errors.rejectValue("birthDate", "error.person.birth-date.pattern");
        }

        final LocalDate max = LocalDate.now();
        final LocalDate min = LocalDate.of(1900, 1, 1);
        try {
        	final LocalDate birthDate = LocalDate.parse(person.getBirthDate(), DateTimeFormatter.ofPattern(birthDateFormat));
            if(birthDate.isAfter(max) || birthDate.isBefore(min)) {
            	errors.rejectValue("birthDate", "error.person.birth-date.minmax");
            }
        } catch(DateTimeParseException dtpe) {
        	errors.rejectValue("birthDate", "error.person.birth-date.format");
        }

	}

}
