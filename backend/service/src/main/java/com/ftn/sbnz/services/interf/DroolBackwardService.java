package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;

import java.util.List;

public interface DroolBackwardService {


    List<GradProgram> executeRules(Professor prof, Student student);
}
