package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DroolForwardService droolForwardService;


    @Override
    public List<GradProgramDTO> recommendGradPrograms(Student student) {
        var recommendations = droolForwardService.executeRules(student);
        return recommendations.stream().map(GradProgramDTO::new).collect(Collectors.toList());

    }

}
