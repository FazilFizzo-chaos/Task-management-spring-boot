package com.example.Automated.Task.Management.Services;

import com.example.Automated.Task.Management.Model.Role;

import java.util.List;

public interface RoleService {
    Role GetByName(String name);
    List<Role> getAllRoles();
}
