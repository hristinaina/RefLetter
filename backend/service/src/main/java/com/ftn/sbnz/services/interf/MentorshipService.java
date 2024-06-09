package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import org.springframework.http.ResponseEntity;

public interface MentorshipService {
    ResponseEntity<?> delete(Long id, Professor professor);

    ResponseEntity<?> create(Long id, Professor professor);


    ResponseEntity<?> getAllMentorshipPrograms(Student student, Long id);
}
