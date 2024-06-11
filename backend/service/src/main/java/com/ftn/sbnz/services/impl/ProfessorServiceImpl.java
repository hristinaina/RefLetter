package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.University;
import com.ftn.sbnz.model.models.dto.ProfessorDTO;
import com.ftn.sbnz.model.repo.ProfessorRepo;
import com.ftn.sbnz.model.repo.UniversityRepo;
import com.ftn.sbnz.services.interf.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {
    @Autowired
    ProfessorRepo professorRepo;

    @Autowired
    UniversityRepo universityRepo;

    @Override
    public List<ProfessorDTO> getAll() {
        return professorRepo.findAll().stream().map(ProfessorDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Professor> update(Professor professor, Professor oldProfessor) {
        if (professor.getId() == oldProfessor.getId()) {
            professor.setId(oldProfessor.getId());
            professor.setPassword(oldProfessor.getPassword());
            professor.setRoles(oldProfessor.getRoles());
            University u = universityRepo.findByName(professor.getUniversity().getName());
            professor.setUniversity(u);
            professorRepo.save(professor);
            return ResponseEntity.ok(professor);
        }
        return ResponseEntity.badRequest().build();
    }
}
