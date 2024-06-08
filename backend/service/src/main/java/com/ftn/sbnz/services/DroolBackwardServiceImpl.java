package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.model.repo.MentorshipRepo;
import com.ftn.sbnz.model.repo.ProfessorRepo;
import com.ftn.sbnz.model.repo.StudentRepo;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DroolBackwardServiceImpl implements DroolBackwardService {
    @Autowired
    private GradProgramRepo gradProgramRepo;
    @Autowired
    private MentorshipRepo mentorshipRepo;
    @Autowired
    private ProfessorRepo professorRepo;

    @Autowired
    private StudentRepo studentRepo;

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
        studentRepo.findAll().forEach(student1 -> factHandles.add(kieSession.insert(student1)));
        factHandles.add(kieSession.insert(student));
        kieSession.setGlobal("mentor", prof);
        List<Professor> results = new ArrayList<>();
        kieSession.setGlobal("results", results);
        kieSession.fireAllRules();

        results.add(prof);
        List<GradProgram> programs = new ArrayList<>();

        results.forEach(result -> programs.addAll(gradProgramRepo.findAllByProfessorId(result.getId())));

        Iterator<GradProgram> iterator = programs.iterator();
        while (iterator.hasNext()) {
            GradProgram program = iterator.next();
            QueryResults results1 = kieSession.getQueryResults("CheckStudentRequirements", student, program);
            if (!(results1.size() > 0)) {
                iterator.remove();
            }
        }
        factHandles.forEach(kieSession::retract);
        return programs;

    }


}
