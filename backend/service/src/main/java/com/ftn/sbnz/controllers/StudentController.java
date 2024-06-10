package com.ftn.sbnz.controllers;


import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.services.interf.CepService;
import com.ftn.sbnz.services.interf.CriteriaTemplateService;
import com.ftn.sbnz.services.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CriteriaTemplateService criteriaTemplateService;

    @Autowired
    private CepService cepService;

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/recommendation")
    public ResponseEntity<?> recommendation() {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(studentService.recommendGradPrograms(student));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('student')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Student student) {
        try {
            var oldStudent = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(studentService.update(student, oldStudent));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics() {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(cepService.updateStudent(student, student));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/notifications")
    public ResponseEntity<?> notifications() {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(studentService.getNotifications(student));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/criteria/{programId}")
    public ResponseEntity<?> checkCriteria(@PathVariable Long programId) {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(criteriaTemplateService.checkCriteria(programId, student));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
