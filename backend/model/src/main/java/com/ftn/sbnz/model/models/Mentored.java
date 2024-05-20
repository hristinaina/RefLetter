package com.ftn.sbnz.model.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Position;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Mentored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @Position(0)
    private Professor mentor;
    @ManyToOne
    @Position(1)
    private Professor student;

    public Mentored(Professor mentor, Professor student) {
        this.mentor = mentor;
        this.student = student;
    }
}
