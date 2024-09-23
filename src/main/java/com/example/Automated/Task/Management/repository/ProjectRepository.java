package com.example.Automated.Task.Management.repository;

import com.example.Automated.Task.Management.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}

