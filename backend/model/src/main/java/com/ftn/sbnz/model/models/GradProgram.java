package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//TODO da li je bitno ko je kreator? Bolje samo univerzitet sacuvati
public class GradProgram {
    private double price;
    private University university;
    private Requirement requirement;
}
