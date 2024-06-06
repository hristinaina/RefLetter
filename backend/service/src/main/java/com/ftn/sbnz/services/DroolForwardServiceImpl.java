package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.GradProgramRecommendation;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.FinancialAidRepo;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn({"gradProgramRepo", "financialAidRepo"})
public class DroolForwardServiceImpl implements DroolForwardService {

    private KieSession kieSession;

    @Autowired
    private GradProgramRepo gradProgramRepo;
    @Autowired
    private FinancialAidRepo financialAidRepo;

    @Autowired
    public DroolForwardServiceImpl(KieSession fwKsession) {
        this.kieSession = fwKsession;
    }

    @PostConstruct
    public void init() {

    }

    @Override
    public List<GradProgramRecommendation> executeRules(Student student) {
        List<FactHandle> factHandles = new ArrayList<>();
        gradProgramRepo.findAll().forEach(gradProgram -> factHandles.add(kieSession.insert(gradProgram)));
        financialAidRepo.findAll().forEach(financialAid -> factHandles.add(kieSession.insert(financialAid)));

        FactHandle studentFactHandle = kieSession.insert(student);
        factHandles.add(studentFactHandle);

        List<GradProgramRecommendation> results = new ArrayList<>();
        kieSession.setGlobal("results", results);
        kieSession.fireAllRules();
        factHandles.forEach(kieSession::retract);
        return results;
    }
}