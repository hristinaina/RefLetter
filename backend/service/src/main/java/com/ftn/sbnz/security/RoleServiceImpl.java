package com.ftn.sbnz.security;

import com.ftn.sbnz.model.models.security.Role;
import com.ftn.sbnz.model.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Role findById(Long id) {
        Role auth = this.roleRepo.getOne(id);
        return auth;
    }

    @Override
    public List<Role> findByName(String name) {
        List<Role> roles = this.roleRepo.findByName(name);
        return roles;
    }
}
