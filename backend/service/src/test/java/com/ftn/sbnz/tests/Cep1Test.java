package com.ftn.sbnz.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.Requirement;
import com.ftn.sbnz.model.models.Student;

@SpringBootTest
public class Cep1Test {

    @Test
    public void TestNewFinancialAid() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession kieSession = kContainer.newKieSession("cep1Ksession");

		kieSession.getAgenda().getAgendaGroup("new-aid").setFocus(); 
		List<Notification> notificationList = new ArrayList<>();
        kieSession.setGlobal("notificationList", notificationList);

        FinancialAid aid = new FinancialAid(
            1L,
            new Requirement(Set.of("ai", "data science")),
            new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2))
        );
        List<Student> students = createStudents();
        for (Student s : students) {
            kieSession.insert(s);
        }

		kieSession.insert(aid);

        int fired = kieSession.fireAllRules();

		assertEquals(2, fired);
	}

    @Test
	public void TestFinancialAidDeadlines() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession kieSession = kContainer.newKieSession("cep1Ksession");

        kieSession.getAgenda().getAgendaGroup("aid-deadline").setFocus();
		List<Notification> notificationList = new ArrayList<>();
        kieSession.setGlobal("notificationList", notificationList);

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

        List<Student> students = createStudents();
        for (Student s : students) {
            kieSession.insert(s);
        }

		kieSession.insert(f1);
		kieSession.insert(f2);

        int fired = kieSession.fireAllRules();
       
		assertEquals(3, fired);
    }

        
    private List<Student> createStudents(){
        List<Student> students = new ArrayList<>();
        HashSet<String> interests1 = new HashSet<>();
        HashSet<String> interests2 = new HashSet<>();
        HashSet<String> interests3 = new HashSet<>();
		interests2.add(new String("ai"));
		interests3.add(new String("ai"));
		interests3.add(new String("gaming"));
		interests3.add(new String("animation"));
        Student s1 = new Student((long) 1, interests1,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));	
		Student s2 = new Student((long) 2, interests2,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));
		Student s3 = new Student((long) 3, interests3,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3)));
        students.add(s1);
        students.add(s2);
        students.add(s3);

        return students;
    }


}
