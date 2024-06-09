package com.ftn.sbnz.services.impl;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ftn.sbnz.model.repo.FinancialAidRepo;
import com.ftn.sbnz.model.repo.NotificationRepo;
import com.ftn.sbnz.model.repo.StudentRepo;
import com.ftn.sbnz.services.KieSessionUtil;
import com.ftn.sbnz.services.interf.CepService;
import org.drools.template.ObjectDataCompiler;
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
public class CepServiceImpl implements InitializingBean, CepService {

	private static Logger log = LoggerFactory.getLogger(CepServiceImpl.class);
	private final KieContainer kieContainer;
	private final KieSession cep2Session;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	FinancialAidRepo financialAidRepo;

	@Autowired
	NotificationRepo notificationRepo;

	@Autowired
	public CepServiceImpl(KieContainer kieContainer) {
		log.info("Initialising a new session.");
		this.kieContainer = kieContainer;
		this.cep2Session = kieContainer.newKieSession("cep2Ksession");
	}

	@Override
    public void afterPropertiesSet() {
        scheduler.scheduleAtFixedRate(this::checkFinancialAidDeadlines, 3, 60*24, TimeUnit.MINUTES);
    }

	public Map<String, Integer> updateStudent(Student newStudent) {

		//todo: studenta dobaviti iz baze po id-ju
		Set<String> interests1 = new HashSet<>();
		interests1.add(new String("ai"));
		interests1.add(new String("gaming"));
		interests1.add(new String("animation"));
		Student oldStudent = new Student((long) 1, interests1,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));

		Map<String, Integer> frequentInterestsMap = new HashMap<>();
        KieSession kieSession = this.cep2Session;
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
		List<Student> students = studentRepo.findAll();
		KieSession kieSession = kieContainer.newKieSession("cep1Ksession");;
		kieSession.getAgenda().getAgendaGroup("new-aid").setFocus(); 

		List<Notification> notificationList = new ArrayList<>();
        kieSession.setGlobal("notificationList", notificationList);

		kieSession.insert(students);
		kieSession.insert(aid);

		int fired = kieSession.fireAllRules();
		kieSession.dispose();

		notificationList = notificationRepo.saveAll(notificationList);
		log.info("Fired {} rules. Notifications: {}", fired, notificationList);
		return notificationList;
	}

	public void checkFinancialAidDeadlines() {
		List<Student> students = studentRepo.findAll();
		List<FinancialAid> aids = financialAidRepo.findAll();

        KieSession kieSession = kieContainer.newKieSession("cep1Ksession");
		kieSession.getAgenda().getAgendaGroup("aid-deadline").setFocus();

        List<Notification> notificationList = new ArrayList<>();
        kieSession.setGlobal("notificationList", notificationList);

        kieSession.insert(students);
		kieSession.insert(aids);

        int fired = kieSession.fireAllRules();
        kieSession.dispose();

		notificationList = notificationRepo.saveAll(notificationList);

        log.info("Fired {} rules. Notifications: {}", fired, notificationList);
    }

	public Boolean checkCriteria(Long i) {
		InputStream template = CepServiceImpl.class.getResourceAsStream("/rules/template/criteria_template.drt");
        List<Requirement> data = new ArrayList<>();
        data.add(new Requirement(3.5, new HashSet<>(), Map.of("GRE", 320.0), Set.of("Research in AI")));
        data.add(new Requirement(3.7, new HashSet<>(), Map.of("GRE", 325.0), Set.of("Research in Machine Learning")));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
		KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        //Results results = kieHelper.verify();
        KieSession ksession = kieHelper.build().newKieSession();

        Student john = new Student("John", "Doe","john@gmail.com", "password", 3.6, "USA", Set.of("Computer Science", "AI"), Map.of("GRE", 322.0), Set.of("Research in AI"),true);
        Student jane = new Student("Jane", "Doe","jane@gmail.com", "password", 3.8,"USA",  Set.of("Computer Science", "Machine Learning"), Map.of("GRE", 328.0), Set.of("Research in Machine Learning"),false);

        ksession.insert(john);
        ksession.insert(jane);

        int fired = ksession.fireAllRules();
		System.out.println(fired);
		return true;
	}
}
