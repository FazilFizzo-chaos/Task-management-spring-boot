package com.example.Automated.Task.Management.Services;

import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.dto.UserDTO;
import com.example.Automated.Task.Management.dto.UserRegistrationDto;

import java.util.List;
import java.util.Set;


public interface UserService {
    List<Users> findAll();
    Users findOne(String username);
    List<UserDTO> getEmployees();
    Users save(UserRegistrationDto userRegistrationDto);
    Users updateUserRoles(Long id, Set<Role> role);
    void changePassword(Long id, String currentPassword , String newPassword);
    void changeEmail(Long id, String currentEmail, String newEmail);
}
