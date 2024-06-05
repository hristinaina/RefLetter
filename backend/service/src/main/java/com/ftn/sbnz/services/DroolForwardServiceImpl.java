package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.GradProgramRecommendation;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.FinancialAidRepo;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
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
        gradProgramRepo.findAll().forEach(kieSession::insert);
        financialAidRepo.findAll().forEach(kieSession::insert);
    }

    @Override
    public List<GradProgramRecommendation> executeRules(Student student) {
        kieSession.insert(student);
        List<GradProgramRecommendation> results = new ArrayList<>();
        kieSession.setGlobal("results", results);
        kieSession.fireAllRules();
        return results;
    }
}