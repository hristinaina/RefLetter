package com.ftn.sbnz.model.models;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double gpa;
    @ElementCollection
    private List<String> researchInterest;

    public Requirement(double gpa, List<String> researchInterest, Map<String, Double> testScores, List<String> researchExperience) {
        this.gpa = gpa;
        this.researchInterest = researchInterest;
        this.testScores = testScores;
        this.researchExperience = researchExperience;
    }

    @ElementCollection
    private Map<String, Double> testScores;
    @ElementCollection
    private List<String> researchExperience;
}
