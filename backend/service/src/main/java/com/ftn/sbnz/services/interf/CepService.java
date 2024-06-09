package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.Notification;
import com.ftn.sbnz.model.models.Student;

import java.util.List;
import java.util.Map;

public interface CepService {
    List<Notification> newFinancialAid(FinancialAid aid);
    Map<String, Integer> updateStudent(Student newStudent);

}
