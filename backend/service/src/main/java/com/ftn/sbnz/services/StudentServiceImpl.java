package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.GradProgramRecommendation;
import com.ftn.sbnz.model.models.Student;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DroolForwardService droolForwardService;

    @Autowired
    private DroolFilterTemplateService droolFilterTemplateService;

    @Override
    public List<GradProgramRecommendation> recommendGradPrograms(Student student) {
        return droolForwardService.executeRules(student);
    }

    @Override
    public List<GradProgram> filterGradPrograms(FilterTemplateModel filterTemplateModel) {
        return droolFilterTemplateService.executeRules(filterTemplateModel);
    }
}
