package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.NotificationRepo;
import com.ftn.sbnz.model.repo.StudentRepo;
import com.ftn.sbnz.services.interf.CepService;
import com.ftn.sbnz.services.interf.DroolForwardService;
import com.ftn.sbnz.services.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DroolForwardService droolForwardService;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CepService cepService;


    @Override
    public List<GradProgramDTO> recommendGradPrograms(Student student) {
        var recommendations = droolForwardService.executeRules(student);
        return recommendations.stream().map(GradProgramDTO::new).collect(Collectors.toList());

    }

    @Override
    public List<Notification> getNotifications(Student student) {
        return notificationRepo.findByUserId(student.getId());
    }

    @Override
    public ResponseEntity<Student> update(Student student, Student oldStudent) {
        if (student.getId() == oldStudent.getId()) {
            student.setId(oldStudent.getId());
            student.setPassword(oldStudent.getPassword());
            student.setRoles(oldStudent.getRoles());
            student = studentRepo.save(student);
            cepService.updateStudent(student, oldStudent);
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.badRequest().build();
    }

}
