package com.ftn.sbnz.controllers;

import com.ftn.sbnz.MyValidator;
import com.ftn.sbnz.MyValidatorException;
import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.model.models.dto.*;
import com.ftn.sbnz.model.models.Professor;
import com.ftn.sbnz.model.models.Student;
import com.ftn.sbnz.model.models.dto.CredentialsDTO;
import com.ftn.sbnz.model.models.dto.PersonDTO;
import com.ftn.sbnz.model.models.dto.TokenDTO;
import com.ftn.sbnz.model.repo.PersonRepo;
import com.ftn.sbnz.security.jwt.JwtTokenUtil;
import com.ftn.sbnz.services.interf.PersonService;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PersonService personService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@#$%^&(){}\\[\\]:;<>,.?/~_+-=|\\\\]).{8,20}$";
    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PersonDTO dto) {
        try {
            MyValidator.validateRequired(dto.getName(), "name");
            MyValidator.validateRequired(dto.getSurname(), "surname");
            MyValidator.validateRequired(dto.getEmail(), "email");
            MyValidator.validateRequired(dto.getPassword(), "password");

            MyValidator.validateLength(dto.getName(), "name", 100);
            MyValidator.validateLength(dto.getSurname(), "surname", 100);
            MyValidator.validateLength(dto.getEmail(), "email", 100);

            MyValidator.validatePattern(dto.getPassword(), "password", PASSWORD_PATTERN);
        } catch (MyValidatorException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }

        var userWithThisEmail = personRepo.findByEmail(dto.getEmail());
        if (userWithThisEmail != null) {
            return new ResponseEntity<String>("User with that email already exists!", HttpStatus.BAD_REQUEST);
        }

        Person person = null;
        try {
            person = personService.register(dto);
        } catch (MyValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(person, HttpStatus.OK);
    }
    @PermitAll
    @PostMapping("/register/professor")
    public ResponseEntity<?> registerProfessor(@RequestBody ProfessorRegisterDTO dto) {
        try {
            MyValidator.validateRequired(dto.getName(), "name");
            MyValidator.validateRequired(dto.getSurname(), "surname");
            MyValidator.validateRequired(dto.getEmail(), "email");
            MyValidator.validateRequired(dto.getPassword(), "password");

            MyValidator.validateLength(dto.getName(), "name", 100);
            MyValidator.validateLength(dto.getSurname(), "surname", 100);
            MyValidator.validateLength(dto.getEmail(), "email", 100);

            MyValidator.validatePattern(dto.getPassword(), "password", PASSWORD_PATTERN);
        } catch (MyValidatorException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }

        var userWithThisEmail = personRepo.findByEmail(dto.getEmail());
        if (userWithThisEmail != null) {
            return new ResponseEntity<String>("User with that email already exists!", HttpStatus.BAD_REQUEST);
        }

        Person person = null;
        try {
            person = personService.register(dto);
        } catch (MyValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(person, HttpStatus.OK);
    }
    @PermitAll
    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterDTO dto) {
        try {
            MyValidator.validateRequired(dto.getName(), "name");
            MyValidator.validateRequired(dto.getSurname(), "surname");
            MyValidator.validateRequired(dto.getEmail(), "email");
            MyValidator.validateRequired(dto.getPassword(), "password");

            MyValidator.validateLength(dto.getName(), "name", 100);
            MyValidator.validateLength(dto.getSurname(), "surname", 100);
            MyValidator.validateLength(dto.getEmail(), "email", 100);

            MyValidator.validatePattern(dto.getPassword(), "password", PASSWORD_PATTERN);
        } catch (MyValidatorException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }

        var userWithThisEmail = personRepo.findByEmail(dto.getEmail());
        if (userWithThisEmail != null) {
            return new ResponseEntity<String>("User with that email already exists!", HttpStatus.BAD_REQUEST);
        }

        Person person = null;
        try {
            person = personService.register(dto);
        } catch (MyValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsDTO credentialsDTO) {
        try {
            MyValidator.validateRequired(credentialsDTO.getEmail(), "email");
            MyValidator.validateRequired(credentialsDTO.getPassword(), "password");

            MyValidator.validateEmail(credentialsDTO.getEmail(), "email");
        } catch (MyValidatorException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(), credentialsDTO.getPassword());

        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(authReq);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Wrong username or password!", HttpStatus.BAD_REQUEST);
        }catch (DisabledException e) {
            return new ResponseEntity<>("User is disabled!", HttpStatus.BAD_REQUEST);
        }

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        long id = ((Person) auth.getPrincipal()).getId();
        String token = jwtTokenUtil.generateToken(id, credentialsDTO.getEmail(), auth.getAuthorities());
        String refreshToken = jwtTokenUtil.generateRefreshToken(id, credentialsDTO.getEmail());
        TokenDTO tokens = new TokenDTO(token, refreshToken);


        return new ResponseEntity<TokenDTO>(tokens, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('student', 'professor')")
    @GetMapping()
    public ResponseEntity<?> getProfileData() {
        try {
            var person = (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            try{
                Student student = (Student) person;
                return ResponseEntity.ok(student);
            }
            catch (ClassCastException e){
                Professor professor = (Professor) person;
                return ResponseEntity.ok(professor);
            }
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
