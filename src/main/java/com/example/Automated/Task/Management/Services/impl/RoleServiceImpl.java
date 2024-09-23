package com.example.Automated.Task.Management.Services.impl;

import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Services.RoleService;
import com.example.Automated.Task.Management.exception.ResourceNotFoundException;
import com.example.Automated.Task.Management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role GetByName(String name) {
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role with name " + name + " not found"));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
