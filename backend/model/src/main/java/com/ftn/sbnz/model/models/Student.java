package com.ftn.sbnz.model.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import java.util.ArrayList;
import java.util.Date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//TODO check attribue types
public class Student extends Person {
    private double gpa;
    private String location;
    private List<String> researchInterest;
    private Map<String, Double> testScores; 
    private List<String> researchExperience;
    private Date updatedTimestamp;

    public Student(Long id, ArrayList<String> interests, Date updatedTimestamp) {
        this.id = id;
        this.researchInterest = interests;
        this.updatedTimestamp = updatedTimestamp;
    }
    private boolean requiresFinancialAid;

    public Student(String name, String surname, String email, String password, double gpa, String location, List<String> researchInterest, Map<String, Double> testScores, List<String> researchExperience,boolean requiresFinancialAid) {
        super(name, surname, email, password);
        this.gpa = gpa;
        this.location = location;
        this.researchInterest = researchInterest;
        this.testScores = testScores;
        this.researchExperience = researchExperience;
        this.requiresFinancialAid = requiresFinancialAid;
    }
}
