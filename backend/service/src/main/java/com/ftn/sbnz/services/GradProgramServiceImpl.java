package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.FilterTemplateModel;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.GradProgramDTO;
import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
