package com.ftn.sbnz.controllers;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.ProgramDTO;
import com.ftn.sbnz.services.interf.GradProgramService;
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

    @PreAuthorize("hasAnyAuthority('student', 'professor')")
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getProgramDetails(@PathVariable Long id) {
        try {
           return ResponseEntity.ok(gradProgramService.getDetails(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllPrograms() {
        try {
            return ResponseEntity.ok(gradProgramService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @GetMapping("/prof")
    public ResponseEntity<?> getProgramsByProfessor() {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(gradProgramService.getProgramsByProfessor(professor));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('student')")
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody FilterTemplateModel filterTemplateModel) {
        try {
            return ResponseEntity.ok(gradProgramService.filterGradPrograms(filterTemplateModel));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return gradProgramService.delete(id, professor);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ProgramDTO gradProgram) {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(gradProgramService.create(gradProgram, professor));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody GradProgram gradProgram) {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(gradProgramService.update(gradProgram, professor));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
