package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Mentorship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorshipRepo extends JpaRepository<Mentorship, Long> {
    List<Mentorship> findAllByMentorId(Long id);
}
