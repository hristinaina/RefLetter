package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor extends Person{
    @ManyToOne(fetch = FetchType.EAGER)
    private University university;


    public Professor(String name, String surname, String email, String password, University university) {
        super(name, surname, email, password);
        this.university = university;
    }
}

