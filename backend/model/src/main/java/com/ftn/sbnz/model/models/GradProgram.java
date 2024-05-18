package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.events.FinancialAid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Position;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//TODO da li je bitno ko je kreator? Bolje samo univerzitet sacuvati
public class GradProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    @ManyToOne
    private University university;
    @ManyToOne
    private Requirement requirement;
    private String name;
    @OneToMany
    private List<FinancialAid> financialAids;

    public void addFinancialAid(FinancialAid financialAid){
        if (this.financialAids == null) {
            this.financialAids = new ArrayList<>();
        }
        this.financialAids.add(financialAid);
    }

    public GradProgram(double price, University university, Requirement requirement, String name, List<FinancialAid> financialAids) {
        this.price = price;
        this.university = university;
        this.requirement = requirement;
        this.name = name;
        this.financialAids = financialAids;
    }
}
