package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.MentorshipDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MentorshipService {
    ResponseEntity<?> delete(Long id, Professor professor);

    ResponseEntity<?> create(String email, Professor professor);

    ResponseEntity<?> getAllMentorshipPrograms(Student student, Long id);

    List<MentorshipDTO> findAllByMentorId(Long id);
}
