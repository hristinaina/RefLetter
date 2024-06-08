package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GradProgramServiceImpl implements GradProgramService {
    @Autowired
    private GradProgramRepo gradProgramRepo;

    @Override
    public GradProgramDetailsDTO getDetails(Long id) {

        GradProgram gradProgram = gradProgramRepo.findById(id).get();
        var profName = gradProgram.getProfessor().getName() + " " + gradProgram.getProfessor().getSurname();
        var gradProgramDetailsDTO = new GradProgramDetailsDTO(gradProgram.getFinancialAids(), profName, gradProgram.getUniversity().getRank(), gradProgram.getUniversity().getNumberOfStudents(), gradProgram.getUniversity().getStudentPerStaff(), gradProgram.getUniversity().getInternationalStudentPercent(), gradProgram.getUniversity().getOverallScore(), gradProgram.getUniversity().getResearchScore(), gradProgram.getUniversity().getCitationScore());
        return gradProgramDetailsDTO;


    }
}
