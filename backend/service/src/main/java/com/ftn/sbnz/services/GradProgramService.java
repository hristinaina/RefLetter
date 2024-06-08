package com.ftn.sbnz.services;

import com.ftn.sbnz.model.models.dto.GradProgramDetailsDTO;

public interface GradProgramService {

    GradProgramDetailsDTO getDetails(Long id);
}
