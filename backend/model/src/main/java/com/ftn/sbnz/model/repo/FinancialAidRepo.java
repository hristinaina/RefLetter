package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.events.FinancialAid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialAidRepo extends JpaRepository<FinancialAid, Long> {
}
