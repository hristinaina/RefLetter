package com.ftn.sbnz.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.services.ExampleService;


@RestController
public class ExampleController {
	private static Logger log = LoggerFactory.getLogger(ExampleController.class);

	private final ExampleService sampleService;

	@Autowired
	public ExampleController(ExampleService sampleService) {
		this.sampleService = sampleService;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	public void testController() {
		this.sampleService.testFunction();
	}
	
	
}
