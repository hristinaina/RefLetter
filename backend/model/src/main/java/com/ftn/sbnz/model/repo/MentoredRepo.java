package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Mentored;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentoredRepo extends JpaRepository<Mentored, Long> {
    List<Mentored> findAllByMentorId(Long id);
}
