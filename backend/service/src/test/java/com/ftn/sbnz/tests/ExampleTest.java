package com.ftn.sbnz.tests;

import com.ftn.sbnz.model.enums.FinancialAidType;
import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.repo.UniversityRepo;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class ExampleTest {
    @Autowired
    UniversityRepo universityRepository;

    @Test
    public void test() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession kieSession = kContainer.newKieSession("fwKsession");


        // Create some requirements
        Requirement req1 = new Requirement(3.5, Set.of("Computer Science", "AI"), Map.of("GRE", 320.0), Set.of("Research in AI"));
        Requirement req2 = new Requirement(3.7, Set.of("Computer Science", "Machine Learning"), Map.of("GRE", 325.0), Set.of("Research in Machine Learning"));

        University mit = new University("MIT", "Cambridge, MA", "1", 11332, 3.0, 34.0, "100", 100.0, 100.0);
        University stanford = new University("Stanford", "Stanford, CA", "2", 17005, 3.9, 23.0, "98.7", 98.7, 99.9);

// Create some grad programs
        GradProgram mitCS = new GradProgram(50000.0, mit, req1,"Computer Science",new ArrayList<>());
        GradProgram stanfordCS = new GradProgram(60000.0, stanford, req2,"Computer Science",new ArrayList<>());

// Create some students
        Student john = new Student("John", "Doe","john@gmail.com", "password", 3.6, "USA",Set.of("Computer Science", "AI"), Map.of("GRE", 322.0), Set.of("Research in AI"),true);
//        Student john = new Student("John Doe", 3.6, Arrays.asList("Computer Science", "AI"), Map.of("GRE", 322.0), Arrays.asList("Research in AI"));
        Student jane = new Student("Jane", "Doe","jane@gmail.com", "password", 3.8,"USA",  Set.of("Computer Science", "Machine Learning"), Map.of("GRE", 328.0), Set.of("Research in Machine Learning"),false);

        // Create financial aid for MIT
        FinancialAid mitAid = new FinancialAid(FinancialAidType.SCHOLARSHIP, 10000.0, req1, new Date());

        mitCS.addFinancialAid(mitAid);

        kieSession.insert(john);
        kieSession.insert(jane);
        kieSession.insert(mitCS);
        kieSession.insert(stanfordCS);
//        kieSession.insert(mit);
//        kieSession.insert(stanford);
        kieSession.insert(mitAid);
        List<GradProgramRecommendation> results = new ArrayList<>();
        kieSession.setGlobal("results", results);
        kieSession.fireAllRules();
        System.out.println(results.size());
    }
}
