package com.example.Automated.Task.Management.Services;

import com.example.Automated.Task.Management.Model.Project;
import com.example.Automated.Task.Management.dto.ProjectRequest;
import com.example.Automated.Task.Management.dto.ProjectUpdateRequest;

import java.util.List;

public interface ProjectService {
    Project createProject(ProjectRequest project);
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project updateProject(Long id, ProjectUpdateRequest projectUpdateRequest);
    void deleteProject(Long id);
}
