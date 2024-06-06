package com.ftn.sbnz.model.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Position;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Mentorship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @Position(0)
    private Professor mentor;
    @ManyToOne(fetch = FetchType.EAGER)
    @Position(1)
    private Professor student;

    public Mentorship(Professor mentor, Professor student) {
        this.mentor = mentor;
        this.student = student;
    }
}
