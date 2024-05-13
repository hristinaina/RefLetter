package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface UniversityRepo extends JpaRepository<University, Long> {
    University findByName(String name);

    List<University> getAll();
}
