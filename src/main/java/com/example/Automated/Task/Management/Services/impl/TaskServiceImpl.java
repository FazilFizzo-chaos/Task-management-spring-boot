package com.example.Automated.Task.Management.Services.impl;

import com.example.Automated.Task.Management.Model.Project;
import com.example.Automated.Task.Management.Model.Task;
import com.example.Automated.Task.Management.Model.TaskStatus;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.TaskService;
import com.example.Automated.Task.Management.dto.TaskDTO;
import com.example.Automated.Task.Management.dto.TaskRequest;
import com.example.Automated.Task.Management.repository.ProjectRepository;
import com.example.Automated.Task.Management.repository.TaskRepository;
import com.example.Automated.Task.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new task
    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setName(taskRequest.getTaskName());
        task.setDescription(taskRequest.getTaskDescription());
        task.setStatus(TaskStatus.OPEN);  // Default status is OPEN
        task.setStartDate(taskRequest.getStartDate());
        task.setDueDate(taskRequest.getDueDate());

        // Fetch and set the project
        Project project = projectRepository.findProjectByIdWithoutRelations(taskRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        task.setProject(project);

        // Fetch and assign to a user
        Users assignedTo = userRepository.findById(taskRequest.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setAssignedTo(assignedTo);

        // Save the task
        return taskRepository.save(task);
    }

    @Override
    public TaskDTO convertToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setTaskStatus(task.getStatus());
        taskDTO.setStartDate(task.getStartDate());
        taskDTO.setDueDate(task.getDueDate());

        return taskDTO;
    }

    // Update an existing task
    public Task updateTask(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setName(taskRequest.getTaskName());
        task.setDescription(taskRequest.getTaskDescription());
        task.setStatus(taskRequest.getStatus());
        task.setStartDate(taskRequest.getStartDate());
        task.setDueDate(taskRequest.getDueDate());

        // Update project if needed
        if (taskRequest.getProjectId() != null) {
            Project project = projectRepository.findById(taskRequest.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        // Update assigned user if needed
        if (taskRequest.getAssignedToId() != null) {
            Users assignedTo = userRepository.findById(taskRequest.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setAssignedTo(assignedTo);
        }

        return taskRepository.save(task);
    }

    // Get a task by ID
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Delete a task by ID
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }
}
