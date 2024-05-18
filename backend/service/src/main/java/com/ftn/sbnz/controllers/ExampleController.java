package com.ftn.sbnz.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@RequestBody Student updatedStudent) {
		updatedStudent.setUpdatedTimestamp(new Date());

		// ovo se inace dobavi iz baze po id-ju
		ArrayList<String> interests = new ArrayList<>();
		interests.add(new String("ai"));
		interests.add(new String("gaming"));
		interests.add(new String("animation"));
		Student oldStudent = new Student((long) 1, interests,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));

		int fired = this.sampleService.updateStudent(oldStudent, updatedStudent);

		return ResponseEntity.ok().body(fired);
    }
	
}
