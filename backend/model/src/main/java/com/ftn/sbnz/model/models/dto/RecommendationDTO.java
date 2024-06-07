package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.GradProgramRecommendation;
import lombok.Data;

@Data

public class RecommendationDTO {
    Long id;
    private double price;
    String name;
    String universityName;
    String requirementName;
    String location;

    public RecommendationDTO(GradProgramRecommendation recommendation){
        this.id =recommendation.getGradProgram().getId();
        this.price = recommendation.getGradProgram().getPrice();
        this.name = recommendation.getGradProgram().getName();
        this.universityName = recommendation.getGradProgram().getUniversity().getName();
        this.requirementName = recommendation.getGradProgram().getRequirement().toString();
        this.location = recommendation.getGradProgram().getUniversity().getLocation();

    }
}
