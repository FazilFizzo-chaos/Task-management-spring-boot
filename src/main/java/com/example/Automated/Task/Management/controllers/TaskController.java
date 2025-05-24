package com.example.Automated.Task.Management.controllers;

import com.example.Automated.Task.Management.Model.Task;
import com.example.Automated.Task.Management.Services.impl.TaskServiceImpl;
import com.example.Automated.Task.Management.dto.EmployeeTaskDTO;
import com.example.Automated.Task.Management.dto.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    // Create a new task
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest taskRequest) {
        Task task = taskService.createTask(taskRequest);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    // Update an existing task
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER', 'EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        Task updatedTask = taskService.updateTask(id, taskRequest);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    // Get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/employee-task")
    public ResponseEntity<Set<EmployeeTaskDTO>> getTaskOfLoggedInEmployee() {
        Set<EmployeeTaskDTO> tasksForEmployee = taskService.getTaskForLoggedInEmployee();
        return new ResponseEntity<>(tasksForEmployee, HttpStatus.OK);
    }

    // Delete a task by ID
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
