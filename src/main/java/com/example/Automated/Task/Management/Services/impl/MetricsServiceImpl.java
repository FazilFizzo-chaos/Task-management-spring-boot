package com.example.Automated.Task.Management.Services.impl;

import com.example.Automated.Task.Management.Model.ProjectStatus;
import com.example.Automated.Task.Management.Services.MetricsService;
import com.example.Automated.Task.Management.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class MetricsServiceImpl implements MetricsService {

    private final ProjectRepository projectRepository;

    public MetricsServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    // Fetch total projects
    public long getTotalProjects() {
        return projectRepository.count();
    }

    // Fetch total canceled projects
    public long getCancelledProjects() {
        return projectRepository.countByStatus(ProjectStatus.CANCELLED);
    }

    // Fetch total planned projects
    public long getPlannedProjects() {
        return projectRepository.countByStatus(ProjectStatus.PLANNED);
    }

}

