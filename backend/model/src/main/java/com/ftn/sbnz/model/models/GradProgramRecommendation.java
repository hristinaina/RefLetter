package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.events.FinancialAid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradProgramRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;
    @ManyToOne(fetch = FetchType.EAGER)
    private GradProgram gradProgram;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<FinancialAid> financialAids;
    private double score;
    private boolean passesRequirements;


    public void addFinancialAid(FinancialAid financialAid) {
        if (this.financialAids == null) {
            this.financialAids = new HashSet<>();
        }
        this.financialAids.add(financialAid);
    }

    public GradProgramRecommendation(Student student, GradProgram gradProgram) {
        this.student = student;
        this.gradProgram = gradProgram;
        score = 0;
        passesRequirements = false;
        financialAids = new HashSet<>();

    }
}
