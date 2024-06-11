package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;
import com.ftn.sbnz.model.models.dto.ProgramDTO;
import com.ftn.sbnz.model.repo.FinancialAidRepo;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.model.repo.RequirementRepo;
import com.ftn.sbnz.model.repo.UniversityRepo;
import com.ftn.sbnz.services.interf.CepService;
import com.ftn.sbnz.services.interf.DroolFilterTemplateService;
import com.ftn.sbnz.services.interf.GradProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradProgramServiceImpl implements GradProgramService {
    @Autowired
    private GradProgramRepo gradProgramRepo;

    @Autowired
    private RequirementRepo requirementRepo;

    @Autowired
    private FinancialAidRepo financialAidRepo;

    @Autowired
    private CepService cepService;

    @Autowired
    private UniversityRepo universityRepo;

    @Autowired
    private DroolFilterTemplateService droolFilterTemplateService;

    @Override
    public ResponseEntity<?> delete(Long id, Professor professor) {
        try {
            // cascade deletion
            var program = gradProgramRepo.findById(id).get();
            if (program.getProfessor().getId() == professor.getId()) {
                gradProgramRepo.delete(program);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(ProgramDTO program, Professor professor) {
        try {
            GradProgram gp = new GradProgram();
            gp.setName(program.getName());
            gp.setPrice(program.getPrice());
            gp.setProfessor(professor);
            gp.setUniversity(universityRepo.findByName(program.getUniversityName()));
            Requirement requirement = requirementRepo.save(program.getRequirement());
            gp.setRequirement(requirement);
//            Set<FinancialAid> aids = new HashSet<>();
//            for (FinancialAid aid: gp.getFinancialAids()){
//                requirement = requirementRepo.save(aid.getRequirement());
//                aid.setRequirement(requirement);
//                aid = financialAidRepo.save(aid);
//                aids.add(aid);
//
//                cepService.newFinancialAid(aid, gp);
//            }
//            gp.setFinancialAids(aids);
            gp.setFinancialAids(new HashSet<>());
            gp = gradProgramRepo.save(gp);

            return ResponseEntity.ok(new GradProgramDTO(gp));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //this method updates program data and doesn't update financial aid. Financial aid CRUD is handled in separated service
    @Override
    public ResponseEntity<?> update(GradProgram gp, Professor professor) {
        try {
            GradProgram oldProgram = gradProgramRepo.findById(gp.getId()).get();
            if (oldProgram.getProfessor().getId() == professor.getId()) {

                gp.setProfessor(professor);
                gp.setId(oldProgram.getId());
                gp.setUniversity(oldProgram.getUniversity());
                gp.setFinancialAids(oldProgram.getFinancialAids());

                Requirement req = gp.getRequirement();
                req.setId(oldProgram.getRequirement().getId());
                gp.setRequirement(req);
                requirementRepo.save(gp.getRequirement());

                gradProgramRepo.save(gp);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public GradProgramDetailsDTO getDetails(Long id) {

        GradProgram gradProgram = gradProgramRepo.findById(id).get();
        var profName = gradProgram.getProfessor().getName() + " " + gradProgram.getProfessor().getSurname();
        var gradProgramDetailsDTO = new GradProgramDetailsDTO(gradProgram.getFinancialAids(), profName, gradProgram.getUniversity().getRank(), gradProgram.getUniversity().getNumberOfStudents(), gradProgram.getUniversity().getStudentPerStaff(), gradProgram.getUniversity().getInternationalStudentPercent(), gradProgram.getUniversity().getOverallScore(), gradProgram.getUniversity().getResearchScore(), gradProgram.getUniversity().getCitationScore());
        return gradProgramDetailsDTO;


    }

    @Override
    public List<GradProgramDTO> getAll() {
        return gradProgramRepo.findAll().stream().map(GradProgramDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<GradProgramDTO> getProgramsByProfessor(Professor professor) {
        return gradProgramRepo.findAllByProfessorId(professor.getId()).stream().map(GradProgramDTO::new).collect(Collectors.toList());
    }


    @Override
    public List<GradProgramDTO> filterGradPrograms(FilterTemplateModel filterTemplateModel, Student student) {
        if (filterTemplateModel == null) {
            return getAll();
        }
        if (filterTemplateModel.getRank()<=0)
            filterTemplateModel.setRank(3000);

        if (filterTemplateModel.getCitationScore()<=0 || filterTemplateModel.getCitationScore()>100)
            filterTemplateModel.setCitationScore(0);


        if (filterTemplateModel.getResearchScore()<=0 || filterTemplateModel.getResearchScore()>100)
            filterTemplateModel.setResearchScore(0);

        if (filterTemplateModel.getLocation().isEmpty())
            filterTemplateModel.setLocation("Any");

        var filtered = droolFilterTemplateService.executeRules(filterTemplateModel);
        return filtered.stream().map(GradProgramDTO::new).collect(Collectors.toList());
    }

}
