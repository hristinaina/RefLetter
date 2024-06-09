package com.ftn.sbnz.services.impl;

import com.ftn.sbnz.model.events.FinancialAid;
import com.ftn.sbnz.model.models.GradProgram;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Requirement;
import com.ftn.sbnz.model.repo.FinancialAidRepo;
import com.ftn.sbnz.model.repo.GradProgramRepo;
import com.ftn.sbnz.model.repo.RequirementRepo;
import com.ftn.sbnz.services.interf.FinancialAidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FinancialAidServiceImpl implements FinancialAidService {

    @Autowired
    private FinancialAidRepo financialAidRepo;

    @Autowired
    private RequirementRepo requirementRepo;

    @Autowired
    private GradProgramRepo gradProgramRepo;

    @Autowired
    private CepServiceImpl cepService;


    @Override
    public ResponseEntity<FinancialAid> create(FinancialAid aid, Long programId, Professor professor) {
        try {
            GradProgram gp = gradProgramRepo.findById(programId).get();

            if (gp.getProfessor().getId() != professor.getId()) return ResponseEntity.badRequest().build();

            Requirement requirement = requirementRepo.save(aid.getRequirement());
            aid.setRequirement(requirement);
            aid = financialAidRepo.save(aid);

            // when new aid is created, it should be added to corresponding grad program
            Set<FinancialAid> aids = gp.getFinancialAids();
            aids.add(aid);
            gp.setFinancialAids(aids);
            gradProgramRepo.save(gp);

            cepService.newFinancialAid(aid);

            return ResponseEntity.ok(aid);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> update(FinancialAid aid, Long programId, Professor professor) {
        try {
            GradProgram gp = gradProgramRepo.findById(programId).get();
            if (gp.getProfessor().getId() != professor.getId()) return ResponseEntity.badRequest().build();

            FinancialAid oldAid = financialAidRepo.findById(aid.getId()).get();

            Requirement req = aid.getRequirement();
            req.setId(oldAid.getRequirement().getId());
            aid.setRequirement(req);
            requirementRepo.save(req);

            // todo jel treba zvati cep ako se azuriralo

            financialAidRepo.save(aid);
            return ResponseEntity.ok(aid);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id, Long programId, Professor professor) {
        try {
            GradProgram program = gradProgramRepo.findById(programId).get();
            if (program.getProfessor().getId() == professor.getId()) {
                FinancialAid aid = financialAidRepo.findById(id).get();
                Set<FinancialAid> aids = program.getFinancialAids();
                aids.remove(aid);
                program.setFinancialAids(aids);
                gradProgramRepo.save(program);

                financialAidRepo.delete(aid);
                return ResponseEntity.ok().build();
            }
           return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
