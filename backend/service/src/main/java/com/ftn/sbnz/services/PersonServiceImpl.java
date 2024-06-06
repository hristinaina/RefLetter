package com.ftn.sbnz.services;

import com.ftn.sbnz.MyValidatorException;
import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.model.models.dto.PersonDTO;
import com.ftn.sbnz.model.models.security.Role;
import com.ftn.sbnz.model.repo.PersonRepo;
import com.ftn.sbnz.security.RoleService;
import com.ftn.sbnz.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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

    @Override
    public Person register(PersonDTO dto) throws MyValidatorException {
        var person = createNewPassenger(dto);
        return personRepo.save(person);
    }

    private Person createNewPassenger(PersonDTO personDTO) {
        var newPassenger = personDTO.toEntity();
        Set<Role> passengerRole = new HashSet<>(roleService.findByName("student"));
        newPassenger.setRoles(passengerRole);

        String encodedPassword = passwordEncoder.encode(newPassenger.getPassword());
        newPassenger.setPassword(encodedPassword);
        return newPassenger;
    }
}
