package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Mentorship;
import com.ftn.sbnz.model.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
