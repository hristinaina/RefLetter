package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepo extends JpaRepository<Professor, Long> {
    Optional<Professor> findByEmail(String email);
}
