package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PersonDTO {
    private String name;
    private String surname;
    private String email;
    private String password;

    public Person toEntity(){
        return new Person(name, surname, email, password);
    }
}
