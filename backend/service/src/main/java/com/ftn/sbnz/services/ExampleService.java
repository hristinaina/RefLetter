package com.ftn.sbnz.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.FinancialAid;
import  com.ftn.sbnz.model.models.Student;
import  com.ftn.sbnz.model.models.Notification;

@Service
public class ExampleService {

	private static Logger log = LoggerFactory.getLogger(ExampleService.class);

	private final KieContainer kieContainer;
	private final KieSession cepSession;

	@Autowired
	public ExampleService(KieContainer kieContainer) {
		log.info("Initialising a new session.");
		this.kieContainer = kieContainer;
		this.cepSession = kieContainer.newKieSession("cep2Ksession");
	}

	public int updateStudent(Student newStudent) {

		//todo: studenta dobaviti iz baze po id-ju
		ArrayList<String> interests = new ArrayList<>();
		interests.add(new String("ai"));
		interests.add(new String("gaming"));
		interests.add(new String("animation"));
		Student oldStudent = new Student((long) 1, interests,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));
		//todo: ako proslijedim studenta sa istim interesima, ne bi trebalo da se izvrsi
        KieSession kieSession = this.cepSession;

        kieSession.insert(oldStudent);
        kieSession.insert(newStudent);

        int fired = kieSession.fireAllRules();

		KieSessionUtil.removeFromSessionByClass(kieSession, Student.class);

        System.out.println(fired);
		return fired;
		//todo zapravo iskucati neku statistiku u pravilu
	}

	public List<Notification> newFinancialAid(FinancialAid aid) {
		//todo: dobaviti sve studente iz baze i proslijediti ih pravilu
		ArrayList<String> interests1 = new ArrayList<>();
		ArrayList<String> interests2 = new ArrayList<>();
		ArrayList<String> interests3 = new ArrayList<>();
		Student s1 = new Student((long) 1, interests1,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));	
		interests2.add(new String("ai"));
		interests3.add(new String("ai"));
		interests3.add(new String("gaming"));
		interests3.add(new String("animation"));
		Student s2 = new Student((long) 2, interests2,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));
		Student s3 = new Student((long) 3, interests3,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));
		
		KieSession kieSession = kieContainer.newKieSession("cep1Ksession");;
		kieSession.getAgenda().getAgendaGroup("new-aid").setFocus(); 

		List<Notification> notificationList = new ArrayList<>();
        kieSession.setGlobal("notificationList", notificationList);

        kieSession.insert(s1);
        kieSession.insert(s2);
		kieSession.insert(s3);
		kieSession.insert(aid);

        int fired = kieSession.fireAllRules();

		kieSession.dispose();
		//todo napravityi scheduler za deadline pravilo

		System.out.println(fired);
		System.out.println(notificationList);
		return notificationList;
	}
}
