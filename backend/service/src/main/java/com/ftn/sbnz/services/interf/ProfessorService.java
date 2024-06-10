package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.dto.ProfessorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfessorService {
    List<ProfessorDTO> getAll();

    ResponseEntity<Professor> update(Professor professor, Professor oldProfessor);
}
