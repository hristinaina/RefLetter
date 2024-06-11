package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.GradProgram;

import java.util.List;

public interface DroolFilterTemplateService {
    public List<GradProgram> executeRules(FilterTemplateModel filterTemplateModel);
}
