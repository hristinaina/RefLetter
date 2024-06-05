package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.GradProgramRecommendation;
import com.ftn.sbnz.model.models.Student;

import java.util.List;

public interface StudentService {
    public List<GradProgramRecommendation> recommendGradPrograms(Student student);
}
