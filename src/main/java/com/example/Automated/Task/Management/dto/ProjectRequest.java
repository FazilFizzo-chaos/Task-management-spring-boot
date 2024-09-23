package com.example.Automated.Task.Management.dto;

import com.example.Automated.Task.Management.Model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ProjectRequest {

    private String projectName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal budget;
    private ProjectStatus status;
    private Set<Long> assignedEmployeeIds;
    private List<TaskReq> tasks;
}
