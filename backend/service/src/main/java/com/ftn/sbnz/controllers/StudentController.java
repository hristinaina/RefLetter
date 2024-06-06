package com.ftn.sbnz.controllers;


import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.PersonDTO;
import com.ftn.sbnz.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/recommendation")
    public ResponseEntity<?> register() {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(studentService.recommendGradPrograms(student));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
