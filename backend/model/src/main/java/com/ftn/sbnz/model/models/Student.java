package com.ftn.sbnz.model.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person{
    
    public Student(Long id, ArrayList<String> interests, Date updatedTimestamp) {
        this.id = id;
        this.researchInterest = interests;
        this.updatedTimestamp = updatedTimestamp;
    }

    private Long id;
    private double gpa;
    private String location;
    private List<String> researchInterest;
    private Map<String, Double> testScores; 
    private List<String> researchExperience;
    private Date updatedTimestamp;
}
