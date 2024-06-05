package com.ftn.sbnz.model.repo;

import com.ftn.sbnz.model.models.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {
    List<Role> findByName(String name);
}
