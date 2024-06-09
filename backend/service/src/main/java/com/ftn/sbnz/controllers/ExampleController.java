package com.ftn.sbnz.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.ftn.sbnz.services.impl.ExampleService;
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

		Map<String, Integer> interests = this.sampleService.updateStudent(updatedStudent);

		return ResponseEntity.ok().body(interests);
    }
	
	@PostMapping(value = "/newAid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newFinancialAid(@RequestBody FinancialAid aid) {
	
		List<Notification> notificationList = this.sampleService.newFinancialAid(aid);

		return ResponseEntity.ok().body(notificationList);
    }

	// iz tokena izvuci studenta, a ulazni parametar ce da bude id studijskog programa
	@PutMapping(value = "/checkCriteria/{id}")
    public ResponseEntity<?> checkCriteria(@PathVariable String id) {
	
		Boolean flag = this.sampleService.checkCriteria(Long.parseLong(id));

		return ResponseEntity.ok().body(flag);
    }
}
