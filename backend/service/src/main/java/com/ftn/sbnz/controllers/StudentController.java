package com.ftn.sbnz.controllers;


import com.ftn.sbnz.model.models.Student;
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


}
