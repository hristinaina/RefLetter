package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.GradProgramRecommendation;
import com.ftn.sbnz.model.models.Student;

import java.util.List;

public interface StudentService {
    public List<GradProgramRecommendation> recommendGradPrograms(Student student);

    List<GradProgram> filterGradPrograms(FilterTemplateModel filterTemplateModel);
}
