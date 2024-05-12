package com.ftn.sbnz.model.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
//TODO check attribue types
public class Student extends Person{
    private double gpa;
    private String location;
    private List<String> researchInterest;
    private Map<String, Double> testScores; 
    private List<String> researchExperience;
}
