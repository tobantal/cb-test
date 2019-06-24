package ru.cb.app.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Import(PersonJsonMapper.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Маппер должен ")
class PersonJsonMapperTest {
	
	@Autowired
	PersonJsonMapper personJsonMapper;

	@DisplayName("конвертировать Person в Json-строку")
	@Test
	void testPersonToJsonString() {
		
		//fail("Not yet implemented");
	}

	@DisplayName("конвертировать Json-строку в Person")
	@Test
	void testJsonStringToPerson() {
		//fail("Not yet implemented");
	}

}
