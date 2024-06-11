package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String rank;
    private int numberOfStudents;
    private double studentPerStaff;
    private double internationalStudentPercent;
    private String overallScore;
    private double researchScore;
    private double citationScore;
    private String name;

    public University(String name, String location, String rank, int numberOfStudents, double studentPerStaff, double internationalStudentPercent, String overallScore, double researchScore, double citationScore) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.numberOfStudents = numberOfStudents;
        this.studentPerStaff = studentPerStaff;
        this.internationalStudentPercent = internationalStudentPercent;
        this.overallScore = overallScore;
        this.researchScore = researchScore;
        this.citationScore = citationScore;
    }
}
