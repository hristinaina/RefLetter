package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.GradProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradProgramRepo extends JpaRepository<GradProgram, Long> {
     List<GradProgram> findAll();
     List<GradProgram> findAllByProfessorId(Long id);

}
