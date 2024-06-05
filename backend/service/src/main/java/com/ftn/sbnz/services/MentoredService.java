package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.Professor;
import org.springframework.http.ResponseEntity;

public interface MentoredService {
    ResponseEntity<?> delete(Long id, Professor professor);

    ResponseEntity<?> create(Long id, Professor professor);
}
