package com.ftn.sbnz.controllers;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.ftn.sbnz.services.impl.CepServiceImpl;
import  com.ftn.sbnz.model.models.Student;

@RestController
public class ExampleController {
	private static Logger log = LoggerFactory.getLogger(ExampleController.class);
	private final CepServiceImpl sampleService;

	@Autowired
	public ExampleController(CepServiceImpl sampleService) {
		this.sampleService = sampleService;
	}
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@RequestBody Student updatedStudent) {

		updatedStudent.setUpdatedTimestamp(new Date());

		Map<String, Integer> interests = this.sampleService.updateStudent(updatedStudent);

		return ResponseEntity.ok().body(interests);
    }

	// iz tokena izvuci studenta, a ulazni parametar ce da bude id studijskog programa
	@PutMapping(value = "/checkCriteria/{id}")
    public ResponseEntity<?> checkCriteria(@PathVariable String id) {
	
		Boolean flag = this.sampleService.checkCriteria(Long.parseLong(id));

		return ResponseEntity.ok().body(flag);
    }
}
