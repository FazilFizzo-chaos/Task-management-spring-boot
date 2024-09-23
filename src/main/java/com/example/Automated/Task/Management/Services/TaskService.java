package com.example.Automated.Task.Management.Services;

import com.example.Automated.Task.Management.Model.Task;
import com.example.Automated.Task.Management.dto.TaskRequest;

import java.util.List;

public interface TaskService {
    Task createTask(TaskRequest taskRequest);
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task updateTask(Long id, TaskRequest taskRequest);
    void deleteTask(Long id);
}
