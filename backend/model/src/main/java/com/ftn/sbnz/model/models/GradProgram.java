package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.events.FinancialAid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//TODO da li je bitno ko je kreator? Bolje samo univerzitet sacuvati
public class GradProgram {
    private double price;
    private University university;
    private Requirement requirement;
    private String name;
    private List<FinancialAid> financialAids;

    public void addFinancialAid(FinancialAid financialAid){
        this.financialAids.add(financialAid);
    }
}
