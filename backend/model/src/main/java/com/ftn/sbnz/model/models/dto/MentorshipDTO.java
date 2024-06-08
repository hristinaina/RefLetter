package com.ftn.sbnz.model.models.dto;

import com.ftn.sbnz.model.models.Mentorship;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MentorshipDTO {
    Long id;
    String studentName;
    String studentEmail;

    public MentorshipDTO(Mentorship mentorship) {
        this.id = mentorship.getId();
        this.studentName = mentorship.getStudent().getName() + " " + mentorship.getStudent().getSurname();
        this.studentEmail = mentorship.getStudent().getEmail();
    }
}
