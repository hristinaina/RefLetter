package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private University university;
    @OneToMany
    private List<GradProgram> gradPrograms;

    public void addGradProgram(GradProgram gradProgram){
        if (this.gradPrograms == null) {
            this.gradPrograms = new ArrayList<>();
        }
        this.gradPrograms.add(gradProgram);
    }

    public Professor(String name, String surname, String email, String password, University university) {
        super(name, surname, email, password);
        this.university = university;
    }
}

