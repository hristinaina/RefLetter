package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.models.Student;

import java.util.List;

public interface CriteriaTemplateService {
    List<Long> checkCriteria(Long programId, Student student);
}
