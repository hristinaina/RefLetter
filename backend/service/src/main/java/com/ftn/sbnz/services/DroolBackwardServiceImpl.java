package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.model.repo.MentorshipRepo;
import com.ftn.sbnz.model.repo.ProfessorRepo;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DroolBackwardServiceImpl implements DroolBackwardService {
    @Autowired
    private GradProgramRepo gradProgramRepo;
    @Autowired
    private MentorshipRepo mentorshipRepo;
    @Autowired
    private ProfessorRepo professorRepo;

    private KieSession kieSession;

    @Autowired
    public DroolBackwardServiceImpl(KieSession bwKsession) {
        this.kieSession = bwKsession;
    }

    @Override
    public List<GradProgram> executeRules(Professor prof, Student student) {
        List<FactHandle> factHandles = new ArrayList<>();
        gradProgramRepo.findAll().forEach(gradProgram -> factHandles.add(kieSession.insert(gradProgram)));
        mentorshipRepo.findAllByMentorId(prof.getId()).forEach(mentorship -> factHandles.add(kieSession.insert(mentorship)));
        professorRepo.findAll().forEach(professor -> factHandles.add(kieSession.insert(professor)));


        kieSession.setGlobal("mentor", prof);
        List<Professor> results = new ArrayList<>();
        kieSession.setGlobal("results", results);
        kieSession.fireAllRules();
        factHandles.forEach(kieSession::retract);
        List<GradProgram> programs = new ArrayList<>();
        results.forEach(result -> programs.addAll(gradProgramRepo.findAllByProfessorId(result.getId())));

        for(GradProgram program : programs){
            QueryResults results1 = kieSession.getQueryResults("CheckStudentRequirements", student,program );
            if ( !(results1.size()>0))
                programs.remove(program);

        }
        return programs;

    }


}
