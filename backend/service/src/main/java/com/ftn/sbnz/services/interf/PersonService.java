package com.ftn.sbnz.services.interf;

import com.ftn.sbnz.MyValidatorException;
import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.model.models.dto.PersonDTO;

public interface PersonService {
    Person register(PersonDTO dto) throws MyValidatorException;
}

