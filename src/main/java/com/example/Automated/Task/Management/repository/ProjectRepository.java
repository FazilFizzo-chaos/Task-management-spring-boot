package com.example.Automated.Task.Management.repository;

import com.example.Automated.Task.Management.Model.Project;
import com.example.Automated.Task.Management.Model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    long countByStatus(ProjectStatus status);

    //Fetch only project fields(without related entities)
    @Query("SELECT new com.example.Automated.Task.Management.Model.Project(p.id, p.name, p.description, p.startDate, p.endDate, p.status, p.budget) FROM Project p")
    List<Project> findAllProjects();

    @Query("SELECT new com.example.Automated.Task.Management.Model.Project(p.id, p.name, p.description, p.startDate, p.endDate, p.status, p.budget) FROM Project p WHERE p.id = :id")
    Optional<Project> findProjectByIdWithoutRelations(@Param("id") Long id);

    // JPQL query to find projects by status and exclude auditing fields
    @Query("SELECT new com.example.Automated.Task.Management.Model.Project(p.id, p.name, p.description, p.startDate, p.endDate, p.status, p.budget) FROM Project p WHERE p.status = :status")
    List<Project> findProjectsByStatus(@Param("status")ProjectStatus status);
}

