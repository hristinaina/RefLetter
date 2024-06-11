package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;
import com.ftn.sbnz.model.models.dto.ProgramDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GradProgramService {

    ResponseEntity<?> delete(Long id, Professor professor);

    ResponseEntity<?> create(ProgramDTO gp, Professor professor);

    GradProgramDetailsDTO getDetails(Long id);

    List<GradProgramDTO> getAll();


    List<GradProgramDTO> filterGradPrograms(FilterTemplateModel filterTemplateModel, Student student);

    ResponseEntity<?> update(GradProgram gradProgram, Professor professor);

    List<GradProgramDTO> getProgramsByProfessor(Professor professor);
}
