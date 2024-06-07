package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
}
