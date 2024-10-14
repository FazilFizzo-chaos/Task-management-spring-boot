package com.example.Automated.Task.Management.dto;

import com.example.Automated.Task.Management.Model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskReq {
    private String taskName;
    private String taskDescription;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String assignedEmployeeUsername;
}