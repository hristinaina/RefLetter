package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class University {
    private String name;
    private String location;
    private int rank;
    private int numberOfStudents;
    private double studentPerStaff;
    private double internationalStudentPercent;
    private double overallScore;
    private double researchScore;
    private double citationScore;
}
