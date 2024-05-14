package com.ftn.sbnz.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.ftn.sbnz.services.ExampleService;
import  com.ftn.sbnz.model.models.Student;

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
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@RequestBody Student updatedStudent) {
		ArrayList<String> interests = new ArrayList<>();
		interests.add(new String("ai"));
		interests.add(new String("gaming"));
		interests.add(new String("animation"));
		Student oldStudent = new Student((long) 1, interests);  // ovo se inace dobavi iz baze po id-ju
		int fired = this.sampleService.updateStudent(oldStudent, updatedStudent);

		return new ResponseEntity<Integer>(fired, HttpStatus.OK);
    }
	
}
