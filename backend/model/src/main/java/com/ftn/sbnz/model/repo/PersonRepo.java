package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.model.models.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo  extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
    List<Person> findByRoles(Role role);
}
