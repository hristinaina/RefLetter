package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.GradProgramRecommendation;
import com.ftn.sbnz.model.models.Requirement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradProgramDTO {
    Long id;
    private double price;
    String name;
    String universityName;
    Requirement requirement;
    String location;

    public GradProgramDTO(GradProgramRecommendation recommendation){
        this.id =recommendation.getGradProgram().getId();
        this.price = recommendation.getGradProgram().getPrice();
        this.name = recommendation.getGradProgram().getName();
        this.universityName = recommendation.getGradProgram().getUniversity().getName();
        this.requirement = recommendation.getGradProgram().getRequirement();
        this.location = recommendation.getGradProgram().getUniversity().getLocation();

    }
    public GradProgramDTO(GradProgram gradProgram){
        this.id = gradProgram.getId();
        this.price = gradProgram.getPrice();
        this.name = gradProgram.getName();
        this.universityName = gradProgram.getUniversity().getName();
        this.requirement = gradProgram.getRequirement();
        this.location = gradProgram.getUniversity().getLocation();
    }
}
