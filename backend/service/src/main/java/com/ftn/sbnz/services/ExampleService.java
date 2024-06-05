package com.ftn.sbnz.services;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.coyote.http11.upgrade.InternalHttpUpgradeHandler;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.FinancialAid;
import  com.ftn.sbnz.model.models.Student;
import  com.ftn.sbnz.model.models.Notification;
import  com.ftn.sbnz.model.models.Requirement;

@Service
public class ExampleService implements InitializingBean{

	private static Logger log = LoggerFactory.getLogger(ExampleService.class);

	private final KieContainer kieContainer;
	private final KieSession cepSession;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Autowired
	public ExampleService(KieContainer kieContainer) {
		log.info("Initialising a new session.");
		this.kieContainer = kieContainer;
		this.cepSession = kieContainer.newKieSession("cep2Ksession");
	}

	@Override
    public void afterPropertiesSet() {
        scheduler.scheduleAtFixedRate(this::checkFinancialAidDeadlines, 0, 1, TimeUnit.DAYS);
    }

	public Map<String, Integer> updateStudent(Student newStudent) {

		//todo: studenta dobaviti iz baze po id-ju
		Set<String> interests1 = new HashSet<>();
		interests1.add(new String("ai"));
		interests1.add(new String("gaming"));
		interests1.add(new String("animation"));
		Student oldStudent = new Student((long) 1, interests1,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));

		Map<String, Integer> frequentInterestsMap = new HashMap<>();
        KieSession kieSession = this.cepSession;
		kieSession.setGlobal("frequentInterestsMap", frequentInterestsMap);

        kieSession.insert(oldStudent);
        kieSession.insert(newStudent);

        int fired = kieSession.fireAllRules();

        System.out.println(fired);
		System.out.println(frequentInterestsMap);
		KieSessionUtil.removeFromSessionByClass(kieSession, Student.class);
		return frequentInterestsMap;
	}

	public List<Notification> newFinancialAid(FinancialAid aid) {
		//todo: dobaviti sve studente iz baze i proslijediti ih pravilu
		HashSet<String> interests1 = new HashSet<>();
		HashSet<String> interests2 = new HashSet<>();
		HashSet<String> interests3 = new HashSet<>();
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

		System.out.println(fired);
		System.out.println(notificationList);
		return notificationList;
	}

	public void checkFinancialAidDeadlines() {
		//todo financialAid dobaviti iz baze, i studente isto
        FinancialAid f1 = new FinancialAid(
                1L,
                new Requirement(Set.of("ai", "data science")),
                new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2))
        );
		FinancialAid f2 = new FinancialAid(
			2L,
			new Requirement(Set.of("gaming", "data science")),
			new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2))
		);
		HashSet<String> interests2 = new HashSet<>();
		HashSet<String> interests3 = new HashSet<>();
		interests2.add(new String("ai"));
		interests3.add(new String("ai"));
		interests3.add(new String("gaming"));
		Student s2 = new Student((long) 2, interests2,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));
		Student s3 = new Student((long) 3, interests3,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));

        KieSession kieSession = kieContainer.newKieSession("cep1Ksession");
		kieSession.getAgenda().getAgendaGroup("aid-deadline").setFocus();

        List<Notification> notificationList = new ArrayList<>();
        kieSession.setGlobal("notificationList", notificationList);

        kieSession.insert(s2);
		kieSession.insert(s3);
		kieSession.insert(f1);
		kieSession.insert(f2);

        int fired = kieSession.fireAllRules();
        kieSession.dispose();

        log.info("Fired {} rules. Notifications: {}", fired, notificationList);
    }
}
