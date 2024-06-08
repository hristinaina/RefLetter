package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;

import java.util.List;

public interface GradProgramService {

    GradProgramDetailsDTO getDetails(Long id);

    List<GradProgramDTO> getAll();


    List<GradProgramDTO> filterGradPrograms(FilterTemplateModel filterTemplateModel, Student student);
}
