package com.ftn.sbnz.services;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.ftn.sbnz.model.models.Student;

@Service
public class ExampleService {

	private static Logger log = LoggerFactory.getLogger(ExampleService.class);

	private final KieContainer kieContainer;

	@Autowired
	public ExampleService(KieContainer kieContainer) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
	}

	public void testFunction() {
		System.out.println("Pooozdrav!!!");
	}

	public int updateStudent(Student oldStudent, Student newStudent) {
        KieSession kieSession = this.kieContainer.newKieSession("cepKsession");
        
        kieSession.insert(oldStudent);
        kieSession.insert(newStudent);
        
        int fired = kieSession.fireAllRules();
        System.out.println(fired);
		return fired;
	}
}
