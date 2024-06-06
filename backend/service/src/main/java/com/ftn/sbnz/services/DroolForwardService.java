package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.GradProgramRecommendation;
import com.ftn.sbnz.model.models.Student;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface DroolForwardService {


    List<GradProgramRecommendation> executeRules(Student student);
}
