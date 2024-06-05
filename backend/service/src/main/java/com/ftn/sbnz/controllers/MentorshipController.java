package com.ftn.sbnz.controllers;

import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.MentoredRepo;
import com.ftn.sbnz.services.MentoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentorship")
public class MentorshipController {
    @Autowired
    private MentoredRepo mentoredRepo;

    @Autowired
    private MentoredService mentoredService;

    @PreAuthorize("hasAuthority('professor')")
    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(mentoredRepo.findAllByMentorId(professor.getId()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return mentoredService.delete(id, professor);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('professor')")
    @PostMapping()
    public ResponseEntity<?> add(@RequestBody Long id) {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(mentoredService.create(id, professor));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
