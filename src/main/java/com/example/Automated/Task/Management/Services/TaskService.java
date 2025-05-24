package com.example.Automated.Task.Management.Services;

import com.example.Automated.Task.Management.Model.Task;
import com.example.Automated.Task.Management.dto.EmployeeTaskDTO;
import com.example.Automated.Task.Management.dto.TaskDTO;
import com.example.Automated.Task.Management.dto.TaskRequest;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface TaskService {
    Task createTask(TaskRequest taskRequest);
    TaskDTO convertToTaskDTO(Task task);
    Set<EmployeeTaskDTO> getTaskForLoggedInEmployee();
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task updateTask(Long id, TaskRequest taskRequest);
    void deleteTask(Long id);
}
