package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.model.repo.UniversityRepo;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DroolFilterTemplateServiceImpl implements DroolFilterTemplateService {

    @Autowired
    GradProgramRepo gradProgramRepo;

    @Autowired
    UniversityRepo universityRepo;

    @Override
    public List<GradProgram> executeRules(FilterTemplateModel filterTemplateModel) {
        InputStream template = DroolFilterTemplateServiceImpl.class.getResourceAsStream("/rules/template/filter_template.drt");

        List<FilterTemplateModel> data = Arrays.asList(filterTemplateModel);

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        KieSession kieSession = this.createKieSessionFromDRL(drl);


        gradProgramRepo.findAll().forEach(kieSession::insert);
        universityRepo.findAll().forEach(kieSession::insert);
        List<GradProgram> results = new ArrayList<>();
        kieSession.setGlobal("results", results);
        kieSession.fireAllRules();
        kieSession.destroy();
        return results;
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
