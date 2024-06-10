package com.ftn.sbnz.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.util.*;

import org.drools.template.ObjectDataCompiler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.test.context.SpringBootTest;

import com.ftn.sbnz.model.models.Requirement;
import com.ftn.sbnz.model.models.Student;

@Disabled
@SpringBootTest
public class CriteriaTemplateTest {
    
    @Test
    public void testCriteriaTemplate() {
        InputStream template = CriteriaTemplateTest.class.getResourceAsStream("/rules/template/criteria_template.drt");
        System.out.println(template);
        List<Requirement> data = new ArrayList<>();
        data.add(new Requirement(3.5, new HashSet<>(), Map.of("GRE", 320.0), Set.of("Research in AI")));
        data.add(new Requirement(3.7, new HashSet<>(), Map.of("GRE", 325.0), Set.of("Research in Machine Learning")));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        System.out.println(drl);

        KieSession ksession = this.createKieSessionFromDRL(drl);

        doTest(ksession);
    }

    private void doTest(KieSession ksession){
        
        Student john = new Student("John", "Doe","john@gmail.com", "password", 3.6, "USA", Set.of("Computer Science", "AI"), Map.of("GRE", 322.0), Set.of("Research in AI"),true);
        Student jane = new Student("Jane", "Doe","jane@gmail.com", "password", 3.8,"USA",  Set.of("Computer Science", "Machine Learning"), Map.of("GRE", 328.0),Set.of("Research in Machine Learning"),false);

        ksession.insert(john);
        ksession.insert(jane);

        int fired = ksession.fireAllRules();
        assertEquals(3, fired);
    }

    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }
}
