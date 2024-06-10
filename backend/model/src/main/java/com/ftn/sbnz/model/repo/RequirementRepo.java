package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepo  extends JpaRepository<Requirement, Long> {
}
