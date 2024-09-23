package com.example.Automated.Task.Management.repository;

import com.example.Automated.Task.Management.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
