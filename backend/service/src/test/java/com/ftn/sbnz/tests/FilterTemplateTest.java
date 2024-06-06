package com.ftn.sbnz.tests;

import com.ftn.sbnz.model.models.*;
import org.drools.template.ObjectDataCompiler;
import org.junit.jupiter.api.Test;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.*;

@SpringBootTest
public class FilterTemplateTest {

    @Test
    public void testSimpleTemplateWithObjects() {
        InputStream template = FilterTemplateTest.class.getResourceAsStream("/rules/template/filter_template.drt");

        List<FilterTemplateModel> data = new ArrayList<>();
        data.add(new FilterTemplateModel(1.0, "Cambridge, MA", 1.0, 1.0));
        data.add(new FilterTemplateModel(2.0, "USA", 2.0, 2.0));
        data.add(new FilterTemplateModel(3.0, "Stanford", 3.0, 3.0));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        System.out.println(drl);

        KieSession ksession = this.createKieSessionFromDRL(drl);

        doTest(ksession);
    }

    private void doTest(KieSession ksession){
        Requirement req1 = new Requirement(3.5, Set.of("Computer Science", "AI"), Map.of("GRE", 320.0), Set.of("Research in AI"));
        Requirement req2 = new Requirement(3.7, Set.of("Computer Science", "Machine Learning"), Map.of("GRE", 325.0), Set.of("Research in Machine Learning"));

        University mit = new University("MIT", "Cambridge, MA", "1", 11332, 3.0, 34.0, "100", 100.0, 100.0);
        University stanford = new University("Stanford", "Stanford, CA", "2", 17005, 3.9, 23.0, "98.7", 98.7, 99.9);

// Create some grad programs
        GradProgram mitCS = new GradProgram(50000.0, mit, req1,"Computer Science",new ArrayList<>());
        GradProgram stanfordCS = new GradProgram(60000.0, stanford, req2,"Computer Science",new ArrayList<>());

        // Initialize more grad programs as needed...

        ksession.insert(mit);
        ksession.insert(mitCS);
        ksession.insert(stanford);
        ksession.insert(stanfordCS);

        List<GradProgram> results = new ArrayList<>();
        ksession.setGlobal("results", results);

        ksession.fireAllRules();

        for(GradProgram gradProgram : results){
            System.out.println(gradProgram.getName());
        }

        // Add assertions to check the results of the rules
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