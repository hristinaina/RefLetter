package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.MyValidatorException;
import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.model.models.dto.PersonDTO;
import com.ftn.sbnz.model.models.dto.ProfessorRegisterDTO;
import com.ftn.sbnz.model.models.dto.StudentRegisterDTO;

public interface PersonService {
    Person register(PersonDTO dto) throws MyValidatorException;

    Person register(ProfessorRegisterDTO dto) throws MyValidatorException;

    Person register(StudentRegisterDTO dto) throws MyValidatorException;
}

