package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;
import com.ftn.sbnz.model.repo.FinancialAidRepo;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.model.repo.RequirementRepo;
import com.ftn.sbnz.model.repo.UniversityRepo;
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
    private CepServiceImpl cepService;

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
    public ResponseEntity<?> create(GradProgram gp, Professor professor) {
        try {
            gp.setProfessor(professor);
            gp.setUniversity(universityRepo.findById(gp.getUniversity().getId()).orElseThrow(
                    ChangeSetPersister.NotFoundException::new));
            Requirement requirement = requirementRepo.save(gp.getRequirement());
            gp.setRequirement(requirement);
            Set<FinancialAid> aids = new HashSet<>();
            for (FinancialAid aid: gp.getFinancialAids()){
                requirement = requirementRepo.save(aid.getRequirement());
                aid.setRequirement(requirement);
                aid = financialAidRepo.save(aid);
                aids.add(aid);

                cepService.newFinancialAid(aid);
            }
            gp.setFinancialAids(aids);
            gradProgramRepo.save(gp);

            return ResponseEntity.ok().build();
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
