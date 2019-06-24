package ru.cb.app.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import ru.cb.app.domain.Person;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Класс должен ")
@Import(PersonValidator.class)
class PersonValidatorTest {

	@Autowired
	@Qualifier("personValidator")
	private PersonValidator personValidator;
	
	private Person person;
	private Errors errors;
	
	@BeforeEach
	private void setUp() {
		person = new Person();
		person.setLastName("Mikhailov");
		person.setFirstName("Oleg");
		person.setMobilePhone("777-777-77-77");
		person.setWorkPhone("777-555-55-55");
		person.setEmail("mo@mail.ru");
		person.setBirthDate("10.06.1985");
		errors = new BeanPropertyBindingResult(person, "person");
	}
	
	@Test
	@DisplayName("правильно валидировать данные")
	void testValidate() {
		personValidator.validate(person, errors);
		assertTrue(errors.getAllErrors().isEmpty());
	}

	@Test
	@DisplayName("выдавать ошибку при несовпадении первых 3 цифр у мобильного и рабочего телефонов")
	void failWrongMobileAndWorkPhone() {
		person.setMobilePhone("757-777-77-77");
		person.setWorkPhone("777-555-55-55");
		personValidator.validate(person, errors);
		assertFalse(errors.getAllErrors().isEmpty());
	}
	
	@Test
	@DisplayName("выдавать ошибку при дате рождения меньше 01.01.1900 года")
	void failWrongBirthDate() {
		person.setBirthDate("31.12.1899");
		personValidator.validate(person, errors);
		assertFalse(errors.getAllErrors().isEmpty());
	}
	
	@Test
	@DisplayName("выдавать ошибку при слишком длинном имени")
	void failLongFirstName() {
		person.setFirstName("ABCDEFGHIGKLMNOPQRST");;
		personValidator.validate(person, errors);
		assertFalse(errors.getAllErrors().isEmpty());
	}
}
