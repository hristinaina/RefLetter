package com.ftn.sbnz.controllers;


import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.CredentialsDTO;
import com.ftn.sbnz.model.models.dto.PersonDTO;
import com.ftn.sbnz.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
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
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody FilterTemplateModel filterTemplateModel) {
        try {
            var student = (Student) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(studentService.filterGradPrograms(filterTemplateModel));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
