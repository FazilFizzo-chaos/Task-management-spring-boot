package com.example.Automated.Task.Management.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column(length = 1000)
    private String description;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Users projectManager;

    @ManyToMany
    @JoinTable(
            name = "project_assigned_employees",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Users> assignedEmployees;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    @Column
    private BigDecimal budget;

    @Column
    private Double progress;

    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdAt;

    @LastModifiedBy
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedAt;

    public Project(Long id, String name, String description, LocalDate startDate, LocalDate endDate, ProjectStatus status, BigDecimal budget) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.budget = budget;
    }

}

