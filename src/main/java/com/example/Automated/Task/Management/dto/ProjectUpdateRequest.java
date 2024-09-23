package com.example.Automated.Task.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ProjectUpdateRequest {
    private Long projectId;
    private String projectName;
    private Long projectManagerId;
    private Set<Long> assignedEmployeeIds;
    private List<TaskRequest> tasks;
}

