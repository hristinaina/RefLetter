package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.models.dto.ProfessorDTO;
import com.ftn.sbnz.model.repo.ProfessorRepo;
import com.ftn.sbnz.services.interf.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {
    @Autowired
    ProfessorRepo professorRepo;

    @Override
    public List<ProfessorDTO> getAll() {
        return professorRepo.findAll().stream().map(ProfessorDTO::new).collect(Collectors.toList());
    }
}
