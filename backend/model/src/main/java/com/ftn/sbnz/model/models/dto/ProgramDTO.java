package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDTO {
    private Long id;
    private double price;
    private String universityName;
    private Requirement requirement;
    private String name;
    private Professor professor;

}
