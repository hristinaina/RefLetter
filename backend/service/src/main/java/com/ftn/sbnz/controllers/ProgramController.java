package com.ftn.sbnz.controllers;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.services.GradProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/program")
@CrossOrigin(origins = "*")
public class ProgramController {

    @Autowired
    GradProgramService gradProgramService;

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getProgramDetails(@PathVariable Long id) {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(gradProgramService.getDetails(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
