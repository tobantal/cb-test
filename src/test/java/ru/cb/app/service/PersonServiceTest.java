package ru.cb.app.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import ru.cb.app.domain.Person;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("PersonService должен ")
class PersonServiceTest {
	
	@Autowired
	private PersonService personService;
	
	@DisplayName("корректно находить данные по запросу")
	@Test
	void testFindByQuery() {
		List<Person> persons = personService.findByQuery("gri");
		assertThat(persons).hasSize(1);
		assertThat(persons.get(0).getLastName()).isEqualTo("Grishin");
	}

}
