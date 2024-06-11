package com.ftn.sbnz.model.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.*;

import java.util.ArrayList;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends Person {
    private double gpa;
    private String location;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> researchInterest;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Double> testScores;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> researchExperience;
    private Date updatedTimestamp;

    @PrePersist
    protected void onCreate() {
        this.updatedTimestamp = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTimestamp = new Date();
    }

    public Student(Long id, Set<String> interests, Date updatedTimestamp) {
        this.setId(id);
        this.researchInterest = interests;
        this.updatedTimestamp = updatedTimestamp;
    }
    private boolean requiresFinancialAid;

    public Student(String name, String surname, String email, String password, double gpa, String location, Set<String> researchInterest, Map<String, Double> testScores, Set<String> researchExperience,boolean requiresFinancialAid) {
        super(name, surname, email, password);
        this.gpa = gpa;
        this.location = location;
        this.researchInterest = researchInterest;
        this.testScores = testScores;
        this.researchExperience = researchExperience;
        this.requiresFinancialAid = requiresFinancialAid;
        this.updatedTimestamp = new Date();
    }
}
