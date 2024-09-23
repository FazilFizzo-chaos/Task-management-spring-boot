package com.example.Automated.Task.Management.controllers;

import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Services.impl.RoleServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    //API to fetch all roles
    @GetMapping("/api/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/api/roles/{name}")
    public Role getRoleByName(@PathVariable String name) {
        Role retrievedrole = roleService.GetByName(name);
        return retrievedrole;
    }
}
