package ru.cb.app.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.cb.app.domain.Person;
import ru.cb.app.domain.Work;
import ru.cb.app.repository.WorkRepository;

@Service
public class WorkService {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkService.class);
	
	private WorkRepository workRepository;

	public WorkService(WorkRepository workRepository) {
		this.workRepository = workRepository;
	}
	
	public void save(Work work) {
		workRepository.save(work);
		logger.info(work.toString());
	}
	
	public List<Work> findAll() {
		return workRepository.findAll(); 
	}
	
	public String findCompanyByPerson(Person person) {
		Optional<Work> optional = workRepository.findWorkByFirstNameAndLastName(person.getFirstName(), person.getLastName());
		return optional.isPresent() ? optional.get().getCompany() : null;
	}

	public List<Work> findByQuery(String query) {
		return workRepository.findByQuery(query.toLowerCase());
	}
	
}
