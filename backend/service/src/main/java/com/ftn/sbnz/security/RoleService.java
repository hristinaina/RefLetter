package com.ftn.sbnz.security;

import com.ftn.sbnz.model.models.security.Role;

import java.util.List;

public interface RoleService {
    Role findById(Long id);
    List<Role> findByName(String name);
}
