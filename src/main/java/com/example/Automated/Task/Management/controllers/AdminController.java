package com.example.Automated.Task.Management.controllers;

import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.impl.UserServiceImpl;
import com.example.Automated.Task.Management.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public AdminController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/users")
    public List<Users> getAllUsers() {
    return  userService.getAllUsers();
    }

    //update the role of user
    @PutMapping("/users/{id}/roles")
    public ResponseEntity<Users> updateUserRoles(@PathVariable Long id, @RequestBody Set<Role> roles) {
        Users updatedUser = userService.updateUserRoles(id, roles);
        return ResponseEntity.ok(updatedUser);
    }

    //deleting users
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
