package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.Requirement;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.model.repo.RequirementRepo;
import com.ftn.sbnz.model.repo.StudentRepo;
import com.ftn.sbnz.services.interf.CriteriaTemplateService;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class CriteriaTemplateServiceImpl implements CriteriaTemplateService {

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    GradProgramRepo gradProgramRepo;

    public List<Long> checkCriteria(Long programId, Student student) {
        InputStream template = CepServiceImpl.class.getResourceAsStream("/rules/template/criteria_template.drt");

        List<Requirement> data = new ArrayList<>();
        GradProgram gp = gradProgramRepo.findById(programId).get();
        data.add(gp.getRequirement());

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        //Results results = kieHelper.verify();
        KieSession ksession = kieHelper.build().newKieSession();

        List<Long> programs = new ArrayList<>();
        ksession.setGlobal("results", programs);

        ksession.insert(student);

        int fired = ksession.fireAllRules();

        return programs;
    }
}
