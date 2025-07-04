package com.example.Automated.Task.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTaskDTO {

   private Long id;
   private String name;
   private String description;
   private LocalDate startDate;
   private LocalDate dueDate;
}
