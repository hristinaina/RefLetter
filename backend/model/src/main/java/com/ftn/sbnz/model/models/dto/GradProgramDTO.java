package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.GradProgramRecommendation;
import lombok.Data;

@Data

public class GradProgramDTO {
    Long id;
    private double price;
    String name;
    String universityName;
    String requirementName;
    String location;

    public GradProgramDTO(GradProgramRecommendation recommendation){
        this.id =recommendation.getGradProgram().getId();
        this.price = recommendation.getGradProgram().getPrice();
        this.name = recommendation.getGradProgram().getName();
        this.universityName = recommendation.getGradProgram().getUniversity().getName();
        this.requirementName = recommendation.getGradProgram().getRequirement().toString();
        this.location = recommendation.getGradProgram().getUniversity().getLocation();

    }
    public GradProgramDTO(GradProgram gradProgram){
        this.id = gradProgram.getId();
        this.price = gradProgram.getPrice();
        this.name = gradProgram.getName();
        this.universityName = gradProgram.getUniversity().getName();
        this.requirementName = gradProgram.getRequirement().toString();
        this.location = gradProgram.getUniversity().getLocation();
    }
}
