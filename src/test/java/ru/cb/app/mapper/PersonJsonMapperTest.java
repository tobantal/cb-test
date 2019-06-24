package ru.cb.app.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Import(PersonJsonMapper.class)
@ExtendWith(SpringExtension.class)
@DisplayName("PersonJsonMapper должен ")
class PersonJsonMapperTest {
	
	@Autowired
	PersonJsonMapper personJsonMapper;

	@DisplayName("конвертировать Person в Json-строку")
	@Test
	void testPersonToJsonString() {
		
		//{"id":3,"lastName":"Mikhailov","firstName":"Oleg","workPhone":"555-555-55-55","mobilePhone":"555-555-55-55","email":"a@a","birthDate":"09.09.2000","company":"SOft+"}
		//String jsonString = "{\\"id\\":null,\\"lastName\\":\\"Mikhailov\\",\\"firstName\\":\\"Oleg\\",\\"workPhone\\":\\"555-555-55-55\\",\\"mobilePhone\":\"555-555-55-55\",\"email\":\"a@a\",\"birthDate\":\"09.09.2000\",\"company\":null}"'
		//fail("Not yet implemented");
	}

	@DisplayName("конвертировать Json-строку в Person")
	@Test
	void testJsonStringToPerson() {
		//fail("Not yet implemented");
	}

}
