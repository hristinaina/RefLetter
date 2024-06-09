package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.NotificationRepo;
import com.ftn.sbnz.services.interf.DroolForwardService;
import com.ftn.sbnz.services.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DroolForwardService droolForwardService;

    @Autowired
    private NotificationRepo notificationRepo;


    @Override
    public List<GradProgramDTO> recommendGradPrograms(Student student) {
        var recommendations = droolForwardService.executeRules(student);
        return recommendations.stream().map(GradProgramDTO::new).collect(Collectors.toList());

    }

    @Override
    public List<Notification> getNotifications(Student student) {
        return notificationRepo.findByUserId(student.getId());
    }

}