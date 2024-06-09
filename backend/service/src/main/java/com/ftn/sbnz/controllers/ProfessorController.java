package com.ftn.sbnz.controllers;

import com.ftn.sbnz.services.interf.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professor")
@CrossOrigin(origins = "*")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllProfessors() {
        try {
            return ResponseEntity.ok(professorService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
