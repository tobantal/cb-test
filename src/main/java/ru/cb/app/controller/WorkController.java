package ru.cb.app.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ru.cb.app.domain.Work;
import ru.cb.app.service.WorkService;

@Controller
@RequestMapping("work")
public class WorkController {

	private static final Logger logger = LoggerFactory.getLogger(WorkController.class);
	
	private WorkService workService;

	public WorkController(WorkService workService) {
		this.workService = workService;
	}

	@GetMapping("create")
	public ModelAndView requestForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("work-creation");
		mav.addObject("work", new Work());
		return mav;
	}
	
	@PostMapping("create")
	public ModelAndView submitForm(@Valid Work work, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView();
			logger.warn("Validation errors while submitting form.");
			mav.setViewName("work-info");
			mav.addObject("work", work);
			return mav;
		}
		
		workService.save(work);
		logger.info("New work: " + work.toString());
		return info(null);
	}
	
	@GetMapping("info")
	public ModelAndView info(@Param("query") String query) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("work-info");
		mav.addObject("works", query!=null ? workService.findByQuery(query) : workService.findAll());
		return mav;
	}

}
