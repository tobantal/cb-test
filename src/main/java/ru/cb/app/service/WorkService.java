package ru.cb.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.cb.app.domain.Person;
import ru.cb.app.domain.Work;
import ru.cb.app.repository.WorkRepository;

@Service
@RequiredArgsConstructor
public class WorkService {

	private static final Logger logger = LoggerFactory.getLogger(WorkService.class);

	private final WorkRepository workRepository;

	public void save(Work work) {
		workRepository.save(work);
		logger.info(work.toString());
	}

	public List<Work> findAll() {
		return workRepository.findAll();
	}

	public String findCompanyByPerson(Person person) {
        return workRepository.findWorkByFirstNameAndLastName(person.getFirstName(), person.getLastName()).map(Work::getCompany).orElse(null);
	}

	public List<Work> findByQuery(String query) {
		return workRepository.findByQuery(query.toLowerCase());
	}

}
