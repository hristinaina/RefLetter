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

	public Map<String, Integer> updateStudent(Student newStudent, Student oldStudent) {

		Map<String, Integer> frequentInterestsMap = new HashMap<>();
        KieSession kieSession = this.cep2Session;
		kieSession.setGlobal("frequentInterestsMap", frequentInterestsMap);

        kieSession.insert(oldStudent);
        kieSession.insert(newStudent);

        int fired = kieSession.fireAllRules();

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
}
