package com.ftn.sbnz.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
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
		ArrayList<String> interests1 = new ArrayList<>();
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

		System.out.println(fired);
		System.out.println(notificationList);
		return notificationList;
	}

	public void checkFinancialAidDeadlines() {
		//todo financialAid dobaviti iz baze, i studente isto
        FinancialAid f1 = new FinancialAid(
                1L,
                new Requirement(Arrays.asList("ai", "data science")),
                new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2))
        );
		FinancialAid f2 = new FinancialAid(
			2L,
			new Requirement(Arrays.asList("gaming", "data science")),
			new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2))
		);
		ArrayList<String> interests2 = new ArrayList<>();
		ArrayList<String> interests3 = new ArrayList<>();
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

	public Boolean checkCriteria(Long i) {
		InputStream template = ExampleService.class.getResourceAsStream("/rules/template/criteria_template.drt");
        List<Requirement> data = new ArrayList<>();
        data.add(new Requirement(3.5, new ArrayList<>(), Map.of("GRE", 320.0), Arrays.asList("Research in AI")));
        data.add(new Requirement(3.7, new ArrayList<>(), Map.of("GRE", 325.0), Arrays.asList("Research in Machine Learning")));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
		KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        //Results results = kieHelper.verify();
        KieSession ksession = kieHelper.build().newKieSession();

        Student john = new Student("John", "Doe","john@gmail.com", "password", 3.6, "USA", Arrays.asList("Computer Science", "AI"), Map.of("GRE", 322.0), Arrays.asList("Research in AI"),true);
        Student jane = new Student("Jane", "Doe","jane@gmail.com", "password", 3.8,"USA",  Arrays.asList("Computer Science", "Machine Learning"), Map.of("GRE", 328.0), Arrays.asList("Research in Machine Learning"),false);

        ksession.insert(john);
        ksession.insert(jane);

        int fired = ksession.fireAllRules();
		System.out.println(fired);
		return true;
	}
}
