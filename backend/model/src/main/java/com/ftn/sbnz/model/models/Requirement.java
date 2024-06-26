package com.ftn.sbnz.model.models;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> researchInterest;

    public Requirement(double gpa, Set<String> researchInterest, Map<String, Double> testScores, Set<String> researchExperience) {
        this.gpa = gpa;
        this.researchInterest = researchInterest;
        this.testScores = testScores;
        this.researchExperience = researchExperience;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Double> testScores;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> researchExperience;

    public Requirement(Set<String> interests) {
        this.researchInterest = interests;
    }
}
