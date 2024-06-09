package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;

import java.util.List;

public interface StudentService {
    public List<GradProgramDTO> recommendGradPrograms(Student student);

}
