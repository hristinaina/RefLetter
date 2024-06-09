package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.services.interf.DroolFilterTemplateService;
import com.ftn.sbnz.services.interf.GradProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradProgramServiceImpl implements GradProgramService {
    @Autowired
    private GradProgramRepo gradProgramRepo;

    @Autowired
    private DroolFilterTemplateService droolFilterTemplateService;

    @Override
    public ResponseEntity<?> delete(Long id, Professor professor) {
        try {
            //todo obrisati financial aid i requirement ili dodati da ide kaskadno (ne i za profesora)
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
            //todo insertovati posebno financialaid i requirement pa ih dodati kao polje zbog id-ja
            gp.setProfessor(professor);
            gp = gradProgramRepo.save(gp);
            System.out.println("POZZZZ");
            System.out.println(gp);
            return ResponseEntity.ok().build();
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
