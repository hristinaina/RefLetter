package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfessorDTO {
    private Long id;
    private String name;
    private String surname;
    private String universityName;
    private String location;

    public ProfessorDTO(Professor professor) {
        this.id = professor.getId();
        this.name = professor.getName();
        this.surname = professor.getSurname();
        this.universityName = professor.getUniversity().getName();
        this.location = professor.getUniversity().getLocation();
    }
}
