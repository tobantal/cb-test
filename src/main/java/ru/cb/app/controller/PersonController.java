package ru.cb.app.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.cb.app.dao.PersonService;
import ru.cb.app.domain.Person;
import ru.cb.app.service.FileManager;

@Controller
@RequestMapping("person")
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	private final PersonService personService;
	private final FileManager fileManager;
	private final Validator validator;
	private final ExecutorService executorService;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	public PersonController(PersonService personService, FileManager fileManager, 
			@Qualifier("personValidator") Validator validator) {
		this.personService = personService;
		this.fileManager = fileManager;
		this.validator = validator;
		this.executorService = Executors.newCachedThreadPool();
	}

	@GetMapping("create")
	public ModelAndView requestForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("person-creation");
		mav.addObject("person", new Person());
		return mav;
	}
	
	@GetMapping("info")
	public ModelAndView info(@Param("query") String query) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("person-info");
		mav.addObject("persons", query!=null ? personService.findByQuery(query) : personService.findAll());
		return mav;
	}
	
	@PostMapping("add")
	public ModelAndView add(@Valid Person person, BindingResult result) {
		if (result.hasErrors()) {
			logger.warn("Validation errors while submitting form."); 
			ModelAndView mav = new ModelAndView();
			mav.setViewName("person-creation");
			mav.addObject("person", person);
			return mav;
		}
		
		String fileName = fileManager.createPrepareFile(person);
		logger.info("New {} added", person);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("person-creation");
		mav.addObject("person", person);
		mav.addObject("fileName", fileName);
		
		return mav;
	}
	
	@GetMapping("push")
	public ModelAndView push(@RequestParam("fileName") String fileName) throws JsonProcessingException {
		executorService.submit(() -> fileManager.movePrepareFile(fileName));
		logger.info("File {} pushed", fileName);
		return clear(fileName);
	}
	
	@GetMapping("clear")
	public ModelAndView clear(@RequestParam String fileName) {
		executorService.submit(() -> fileManager.deletePrepareFile(fileName));
		logger.info("File {} deleted", fileName);
		return requestForm();
	}

}
