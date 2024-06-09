package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.Professor;
import org.springframework.http.ResponseEntity;

public interface FinancialAidService {

    ResponseEntity<FinancialAid> create(FinancialAid aid, Long programId, Professor professor);

    ResponseEntity<?> update(FinancialAid aid, Long programId,Professor professor);

    ResponseEntity<?> delete(Long id, Long programId, Professor professor);
}
