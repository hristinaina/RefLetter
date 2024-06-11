package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.events.FinancialAid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class GradProgramDetailsDTO {
    Set<FinancialAid> financialAids;
    String professorName;
    private String rank;
    private int numberOfStudents;
    private double studentPerStaff;
    private double internationalStudentPercent;
    private String overallScore;
    private double researchScore;
    private double citationScore;

}
