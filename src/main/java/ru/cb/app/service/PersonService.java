package ru.cb.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.cb.app.domain.Person;
import ru.cb.app.repository.PersonRepository;

@Service("personService")
public class PersonService {
	
	private PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	public void save(Person person) {
		personRepository.save(person); 
	}
	
	public List<Person> findAll() {
		return personRepository.findAll(); 
	}

	public List<Person> findByQuery(String query) {
		return personRepository.findByQuery(query.toLowerCase());
	}

}
