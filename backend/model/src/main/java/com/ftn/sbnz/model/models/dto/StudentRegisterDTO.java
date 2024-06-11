package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegisterDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String gpa;
    private String location;
    private Set<String> researchInterest;
    private Map<String, String> testScores;
    private Set<String> researchExperience;

    public Student toEntity() {
        Map<String, Double> testScores = new HashMap<>();
        this.testScores.forEach((k, v) -> testScores.put(k, Double.parseDouble(v)));

        return new Student(name, surname, email, password, Double.parseDouble(gpa), location, researchInterest, testScores, researchExperience, false);
    }
}