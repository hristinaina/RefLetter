package com.ftn.sbnz.services;

import com.ftn.sbnz.MyValidatorException;
import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.dto.PersonDTO;
import com.ftn.sbnz.model.models.dto.ProfessorRegisterDTO;
import com.ftn.sbnz.model.models.dto.StudentRegisterDTO;
import com.ftn.sbnz.model.models.security.Role;
import com.ftn.sbnz.model.repo.PersonRepo;
import com.ftn.sbnz.model.repo.ProfessorRepo;
import com.ftn.sbnz.model.repo.UniversityRepo;
import com.ftn.sbnz.security.RoleService;
import com.ftn.sbnz.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private UniversityRepo universityRepo;
    @Autowired
    private ProfessorRepo professorRepo;



    @Override
    public Person register(PersonDTO dto) throws MyValidatorException {
        var person = createNewPerson(dto);
        return personRepo.save(person);
    }


    @Override
    public Person register(ProfessorRegisterDTO dto) throws MyValidatorException {
        var person = createNewPerson(dto);
        return professorRepo.save(person);
    }
    @Override
    public Person register(StudentRegisterDTO dto) throws MyValidatorException {
        var person = createNewPerson(dto);
        return personRepo.save(person);
    }

    private Person createNewPerson(StudentRegisterDTO dto) {
        var newPerson = dto.toEntity();
        Set<Role> passengerRole = new HashSet<>(roleService.findByName("student"));
        newPerson.setRoles(passengerRole);


        String encodedPassword = passwordEncoder.encode(newPerson.getPassword());
        newPerson.setPassword(encodedPassword);
        return newPerson;
    }

    private Professor createNewPerson(ProfessorRegisterDTO dto) {
        var newPerson = dto.toEntity();
        Set<Role> passengerRole = new HashSet<>(roleService.findByName("professor"));
        newPerson.setRoles(passengerRole);
        newPerson.setUniversity(universityRepo.findByName(dto.getUniversity()));

        String encodedPassword = passwordEncoder.encode(newPerson.getPassword());
        newPerson.setPassword(encodedPassword);
        return newPerson;
    }

    private Person createNewPerson(PersonDTO personDTO) {
        var newPassenger = personDTO.toEntity();
        Set<Role> passengerRole = new HashSet<>(roleService.findByName("student"));
        newPassenger.setRoles(passengerRole);

        String encodedPassword = passwordEncoder.encode(newPassenger.getPassword());
        newPassenger.setPassword(encodedPassword);
        return newPassenger;
    }
}
