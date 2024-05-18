package com.ftn.sbnz.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kie.api.runtime.rule.FactHandle;

import  com.ftn.sbnz.model.models.Student;

@Service
public class ExampleService {

	private static Logger log = LoggerFactory.getLogger(ExampleService.class);

	private final KieContainer kieContainer;
	private final KieSession cepSession;

	@Autowired
	public ExampleService(KieContainer kieContainer) {
		log.info("Initialising a new session.");
		this.kieContainer = kieContainer;
		this.cepSession = kieContainer.newKieSession("cepKsession");
	}

	public int updateStudent(Student oldStudent, Student newStudent) {

        KieSession kieSession = this.cepSession;

        kieSession.insert(oldStudent);
        kieSession.insert(newStudent);

        int fired = kieSession.fireAllRules();

		KieSessionUtil.removeFromSessionByClass(kieSession, Student.class);

        System.out.println(fired);
		return fired;
	}
}
