package ru.cb.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("PersonController должен ")
class PersonControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@DisplayName("принимать запрос на создание формы")
	@Test
	public void requestForm() throws Exception {
		mvc.perform(get("/person/create"))
		.andExpect(status().isOk());
	}

	@DisplayName("принимать запрос на вывод таблицы")
	@Test
	public void info() throws Exception {
		mvc.perform(get("/person/info"))
		.andExpect(status().isOk());
	}
	
	@DisplayName("принимать запрос на отправку файла")
	@Test
	public void push() throws Exception {
		mvc.perform(get("/person/push?fileName=abc"))
		.andExpect(status().isOk());
	}
	
	@DisplayName("принимать запрос на удаление файла")
	@Test
	public void clear() throws Exception {
		mvc.perform(get("/person/clear?fileName=abc"))
		.andExpect(status().isOk());
	}

}
