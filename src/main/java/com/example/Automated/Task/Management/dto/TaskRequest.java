package com.example.Automated.Task.Management.dto;

import com.example.Automated.Task.Management.Model.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaskRequest {
    private String taskName;
    private String taskDescription;
    private TaskStatus status;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Long projectId;
    private Long AssignedToId;
}
