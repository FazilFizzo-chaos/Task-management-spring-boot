package com.example.Automated.Task.Management.repository;

import com.example.Automated.Task.Management.Model.Task;
import com.example.Automated.Task.Management.Model.TaskStatus;
import com.example.Automated.Task.Management.dto.EmployeeTaskDTO;
import com.example.Automated.Task.Management.dto.TaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByStatus(TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.username = :username")
    Set<Task> findByEmployeeUsername(@Param("username") String username);
}
