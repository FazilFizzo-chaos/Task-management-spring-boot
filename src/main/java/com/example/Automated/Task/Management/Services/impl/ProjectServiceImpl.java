package com.example.Automated.Task.Management.Services.impl;

import com.example.Automated.Task.Management.Model.Project;
import com.example.Automated.Task.Management.Model.ProjectStatus;
import com.example.Automated.Task.Management.Model.Task;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.ProjectService;
import com.example.Automated.Task.Management.dto.ProjectRequest;
import com.example.Automated.Task.Management.dto.ProjectUpdateRequest;
import com.example.Automated.Task.Management.dto.TaskReq;
import com.example.Automated.Task.Management.dto.TaskRequest;
import com.example.Automated.Task.Management.exception.ProjectNotFound;
import com.example.Automated.Task.Management.exception.ResourceNotFoundException;
import com.example.Automated.Task.Management.repository.ProjectRepository;
import com.example.Automated.Task.Management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(ProjectRequest projectRequest) {
        // Create a new project
        Project project = new Project();
        project.setName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
        project.setBudget(projectRequest.getBudget());
        project.setStatus(projectRequest.getStatus());

        // Add tasks to the project
        Set<Task> tasks = new HashSet<>();
        for (TaskReq taskRequest : projectRequest.getTasks()) {
            Task task = new Task();
            task.setName(taskRequest.getTaskName());
            task.setDescription(taskRequest.getTaskDescription());
            task.setStatus(taskRequest.getStatus());
            task.setStartDate(taskRequest.getStartDate());
            task.setDueDate(taskRequest.getDueDate());


            // Fetch and assign employees
           String username = taskRequest.getAssignedEmployeeUsername();
            Users assignedEmployee = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            task.setAssignedTo(assignedEmployee);

            tasks.add(task);

            task.setProject(project);// Associate task with the project
        }
        project.setTasks(tasks);

        // Save the project (Cascade will save tasks automatically)
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAllProjects();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findProjectByIdWithoutRelations(id)
                .orElseThrow(() -> new ProjectNotFound("ProjectNotFound"));
    }

    public Project updateProject(Long id, ProjectUpdateRequest projectUpdateRequest) {
        // Fetch the existing project
        Project project = projectRepository.findById(projectUpdateRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Update project name
        project.setName(projectUpdateRequest.getProjectName());

        // Update assigned employees
        Set<Users> assignedEmployees = new HashSet<>();
        for (Long employeeId : projectUpdateRequest.getAssignedEmployeeIds()) {
            Users employee = userRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            assignedEmployees.add(employee);
        }
        project.setAssignedEmployees(assignedEmployees); // Clear and set new employees

        // Update tasks
        Set<Task> tasks = new HashSet<>();
        for (TaskRequest taskRequest : projectUpdateRequest.getTasks()) {
            Task task = new Task();
            task.setName(taskRequest.getTaskName());
            task.setDescription(taskRequest.getTaskDescription());
            task.setProject(project); // Associate task with the project
            tasks.add(task);
        }
        project.setTasks(tasks); // Clear and set new tasks (or update tasks)

        // Save the updated project
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = getProjectById(id);
        projectRepository.delete(project);
    }

    public List<Project> getProjectsByStatus(ProjectStatus projectStatus){
        return projectRepository.findProjectsByStatus(projectStatus);
    }
}
