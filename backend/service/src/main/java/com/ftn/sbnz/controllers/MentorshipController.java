package com.ftn.sbnz.controllers;

import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.MentorshipRepo;
import com.ftn.sbnz.services.interf.MentorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentorship")
@CrossOrigin(origins = "*")
public class MentorshipController {
    @Autowired
    private MentorshipRepo mentorshipRepo;

    @Autowired
    private MentorshipService mentoredService;

    @PreAuthorize("hasAuthority('professor')")
    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            var professor = (Professor) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(mentorshipRepo.findAllByMentorId(professor.getId()));
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

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("mentored/{id}")
    public ResponseEntity<?> getAllMentorshipPrograms(@PathVariable Long id) {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return mentoredService.getAllMentorshipPrograms(student, id);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



}
