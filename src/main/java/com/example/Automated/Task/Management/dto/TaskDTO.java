package com.example.Automated.Task.Management.dto;

import com.example.Automated.Task.Management.Model.TaskStatus;

import java.time.LocalDate;

public class TaskDTO {

    private Long id;
    private String name;
    private String description;
    private TaskStatus taskStatus;
    private LocalDate startDate;
    private LocalDate dueDate;
}
