package com.ftn.sbnz.tests;

import com.ftn.sbnz.model.enums.FinancialAidType;
import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.*;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.*;

public class BackwardsTest {
    @Test
    public void testBackwards() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("bwKsession");

        // Create some requirements
        Requirement req1 = new Requirement(3.5, Arrays.asList("Computer Science", "AI"), Map.of("GRE", 320.0), Arrays.asList("Research in AI"));
        Requirement req2 = new Requirement(3.7, Arrays.asList("Computer Science", "Machine Learning"), Map.of("GRE", 325.0), Arrays.asList("Research in Machine Learning"));

        University mit = new University("MIT", "Cambridge, MA", "1", 11332, 3.0, 34.0, "100", 100.0, 100.0);
        University stanford = new University("Stanford", "Stanford, CA", "2", 17005, 3.9, 23.0, "98.7", 98.7, 99.9);

        // Create some grad programs
        GradProgram mitCS = new GradProgram(50000.0, mit, req1,"Computer Science",new ArrayList<>());
        GradProgram stanfordCS = new GradProgram(60000.0, stanford, req2,"Computer Science",new ArrayList<>());

        // Create some students
        Student john = new Student("John", "Doe","john@gmail.com", "password", 3.6, "USA", Arrays.asList("Computer Science", "AI"), Map.of("GRE", 322.0), Arrays.asList("Research in AI"),true);
        Student jane = new Student("Jane", "Doe","jane@gmail.com", "password", 3.8,"USA",  Arrays.asList("Computer Science", "Machine Learning"), Map.of("GRE", 328.0), Arrays.asList("Research in Machine Learning"),false);

        // Create financial aid for MIT
        FinancialAid mitAid = new FinancialAid(FinancialAidType.SCHOLARSHIP, 10000.0, req1, new Date());

        mitCS.addFinancialAid(mitAid);

        kieSession.insert(john);
        kieSession.insert(jane);
        kieSession.insert(mitCS);
        kieSession.insert(stanfordCS);
        kieSession.insert(mit);
        kieSession.insert(stanford);
        kieSession.insert(mitAid);
        kieSession.setGlobal("globalStudent", john);
        kieSession.setGlobal("globalProgram", mitCS);
        kieSession.fireAllRules();
    }
}