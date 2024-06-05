package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepo extends JpaRepository<Professor, Long> {
}
