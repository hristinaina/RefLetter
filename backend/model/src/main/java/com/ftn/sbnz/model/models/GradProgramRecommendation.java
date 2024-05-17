package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.events.FinancialAid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradProgramRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private GradProgram gradProgram;
    @OneToMany
    private List<FinancialAid> financialAids;
    private double score;
    private boolean passesRequirements;


    public void addFinancialAid(FinancialAid financialAid) {
        if (this.financialAids == null) {
            this.financialAids = new ArrayList<>();
        }
        this.financialAids.add(financialAid);
    }


    public GradProgramRecommendation(Student student, GradProgram gradProgram) {
        this.student = student;
        this.gradProgram = gradProgram;
        score = 0;
        passesRequirements = false;
        financialAids = new ArrayList<>();

    }
}
