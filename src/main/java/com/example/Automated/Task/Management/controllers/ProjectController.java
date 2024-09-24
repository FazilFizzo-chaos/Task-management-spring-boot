package com.example.Automated.Task.Management.controllers;

import com.example.Automated.Task.Management.Model.Project;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.impl.ProjectServiceImpl;
import com.example.Automated.Task.Management.Services.impl.UserServiceImpl;
import com.example.Automated.Task.Management.dto.ProjectRequest;
import com.example.Automated.Task.Management.dto.ProjectUpdateRequest;
import com.example.Automated.Task.Management.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pm")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private UserServiceImpl userService;

    //create a new project
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequest project) {
        Project createdProject = projectService.createProject(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    //Retrieve all projects
    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    //Retrieve a project by id
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    //update an existing project
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectUpdateRequest projectUpdateRequest) {
        Project updatedProject = projectService.updateProject(id, projectUpdateRequest);
        return ResponseEntity.ok(updatedProject);
    }

    //Deleting an existing project
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees")
    public List<UserDTO> getAllEmployees() { return userService.getEmployees(); }

    @GetMapping("/project-managers")
    public List<Users> getAllProjectManagers() {
        return userService.getProjectManagers();
    }
}

