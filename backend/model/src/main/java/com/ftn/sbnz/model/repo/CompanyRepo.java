package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Long> {
}
