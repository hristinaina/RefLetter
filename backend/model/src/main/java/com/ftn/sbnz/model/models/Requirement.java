package com.ftn.sbnz.model.models;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {
    private double gpa;
    private List<String> researchInterest;
    private Map<String, Double> testScores; 
    private List<String> researchExperience;
}
