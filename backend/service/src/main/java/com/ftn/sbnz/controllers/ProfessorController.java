package com.ftn.sbnz.controllers;

import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.services.interf.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('professor')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Professor professor) {
        try {
            var oldProfessor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(professorService.update(professor, oldProfessor));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
