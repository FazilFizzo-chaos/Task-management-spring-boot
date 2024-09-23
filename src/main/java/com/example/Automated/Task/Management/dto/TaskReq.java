package com.example.Automated.Task.Management.dto;

import com.example.Automated.Task.Management.Model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaskReq {
    private String taskName;
    private String taskDescription;
    private TaskStatus status;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
}