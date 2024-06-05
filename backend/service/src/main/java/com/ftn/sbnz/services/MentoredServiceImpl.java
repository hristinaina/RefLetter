package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.Mentored;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.repo.MentoredRepo;
import com.ftn.sbnz.model.repo.ProfessorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class MentoredServiceImpl implements MentoredService {
    @Autowired
    MentoredRepo mentoredRepo;
    @Autowired
    private ProfessorRepo professorRepo;

    @Override
    public ResponseEntity<?> delete(Long id, Professor professor) {
        try {
            var mentored = mentoredRepo.findById(id).get();
            if (mentored.getMentor().getId() == professor.getId()) {
                mentoredRepo.delete(mentored);
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
                var mentorship = new Mentored(professor, mentored);
                mentoredRepo.save(mentorship);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
