package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.dto.RecommendationDTO;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DroolForwardService droolForwardService;

    @Autowired
    private DroolFilterTemplateService droolFilterTemplateService;

    @Override
    public List<RecommendationDTO> recommendGradPrograms(Student student) {
        var recommendations = droolForwardService.executeRules(student);
        return recommendations.stream().map(RecommendationDTO::new).collect(Collectors.toList());

    }

    @Override
    public List<GradProgram> filterGradPrograms(FilterTemplateModel filterTemplateModel) {
        return droolFilterTemplateService.executeRules(filterTemplateModel);
    }
}
