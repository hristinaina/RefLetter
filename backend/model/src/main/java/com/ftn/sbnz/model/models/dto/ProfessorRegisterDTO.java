package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRegisterDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String university;

    public Professor toEntity() {
        return new Professor(name, surname, email, password, null);
    }
}
