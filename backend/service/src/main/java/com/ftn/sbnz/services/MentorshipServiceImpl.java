package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.Mentorship;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.MentorshipRepo;
import com.ftn.sbnz.model.repo.ProfessorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class MentorshipServiceImpl implements MentorshipService {
    @Autowired
    MentorshipRepo mentorshipRepo;
    @Autowired
    private ProfessorRepo professorRepo;

    @Autowired
    DroolBackwardService droolBackwardService;

    @Override
    public ResponseEntity<?> delete(Long id, Professor professor) {
        try {
            var mentored = mentorshipRepo.findById(id).get();
            if (mentored.getMentor().getId() == professor.getId()) {
                mentorshipRepo.delete(mentored);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(Long id, Professor professor) {
        try {
            var mentored = professorRepo.findById(id).get();
            if (mentored.getId() != professor.getId()) {
                var mentorship = new Mentorship(professor, mentored);
                mentorshipRepo.save(mentorship);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> getAllMentorshipPrograms(Student student, Long id) {
        try {
            var professor = professorRepo.findById(id).get();
            return ResponseEntity.ok(droolBackwardService.executeRules(professor, student));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
