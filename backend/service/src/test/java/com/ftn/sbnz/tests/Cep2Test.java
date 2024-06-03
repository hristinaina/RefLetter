package com.ftn.sbnz.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.conf.ClockTypeOption;

import com.ftn.sbnz.model.models.Student;

public class Cep2Test {

    @Test
    public void TestInterestChangeEvent(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession kieSession = kContainer.newKieSession("cep2Ksession");

		Map<String, Integer> frequentInterestsMap = new HashMap<>();
		kieSession.setGlobal("frequentInterestsMap", frequentInterestsMap);

        kieSession.insert(createStudent(0));
        kieSession.insert(createStudent(1));

        int fired = kieSession.fireAllRules();

        System.out.println(fired);
        assertEquals(1, fired);
    }


    @Test
    public void TestIndecisiveStudentEvent(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession kieSession = kContainer.newKieSession("cep2Ksession");
        kieSession.getSessionConfiguration().setOption(ClockTypeOption.get("pseudo"));

        SessionPseudoClock clock = kieSession.getSessionClock();

		Map<String, Integer> frequentInterestsMap = new HashMap<>();
		kieSession.setGlobal("frequentInterestsMap", frequentInterestsMap);

        int fired = 0;
        for (int i = 0; i < 10; i++){
            kieSession.insert(createStudent(i));
            clock.advanceTime(1, TimeUnit.HOURS);
            if (fired == 0) kieSession.insert(createStudent(i-1));
            fired = kieSession.fireAllRules();
        }

        System.out.println(fired);
        assertEquals(3, fired);

        kieSession.insert(createStudent(1));
        clock.advanceTime(1, TimeUnit.HOURS);
        kieSession.insert(createStudent(2));
        fired = kieSession.fireAllRules();
        assertEquals(2, fired);
    }

    
    private Student createStudent(int i){
        ArrayList<String> interests1 = new ArrayList<>();
		interests1.add(new String("ai"));
		interests1.add(new String("gaming"));
		interests1.add(new String("animation"));
        interests1.add("new interest" + i);
		Student student = new Student((long) -1, interests1,  new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(3) + i*100));
        return student;
    }
    
}
