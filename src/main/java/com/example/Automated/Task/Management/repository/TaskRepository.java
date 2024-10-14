package com.example.Automated.Task.Management.repository;

import com.example.Automated.Task.Management.Model.Task;
import com.example.Automated.Task.Management.Model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByStatus(TaskStatus status);
}
