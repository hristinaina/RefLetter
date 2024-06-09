package com.ftn.sbnz.controllers;


import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.dto.FinancialAidDTO;
import com.ftn.sbnz.services.interf.FinancialAidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/aid")
@CrossOrigin(origins = "*")
public class FinancialAidController {

    @Autowired
    FinancialAidService financialAidService;


    @PreAuthorize("hasAuthority('professor')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestBody Map<String, Long> program) {
        try {
            Long programId = program.get("programId");
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return financialAidService.delete(id, programId, professor);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody FinancialAidDTO aid) {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(financialAidService.create(aid.getFinancialAid(), aid.getProgramId(), professor));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody FinancialAidDTO aid) {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(financialAidService.update(aid.getFinancialAid(), aid.getProgramId(), professor));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
