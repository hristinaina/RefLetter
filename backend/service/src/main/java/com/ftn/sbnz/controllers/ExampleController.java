package com.ftn.sbnz.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.ftn.sbnz.services.ExampleService;
import com.ftn.sbnz.model.events.FinancialAid;
import  com.ftn.sbnz.model.models.Student;
import  com.ftn.sbnz.model.models.Notification;

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

		int fired = this.sampleService.updateStudent(updatedStudent);

		return ResponseEntity.ok().body(fired);
    }
	
	@PostMapping(value = "/newAid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newFinancialAid(@RequestBody FinancialAid aid) {
	
		List<Notification> notificationList = this.sampleService.newFinancialAid(aid);

		return ResponseEntity.ok().body(notificationList);
    }
}
