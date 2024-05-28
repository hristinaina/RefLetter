package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterTemplateModel {
    private double rank;
    private String location;
    private double researchScore;
    private double citationScore;
}