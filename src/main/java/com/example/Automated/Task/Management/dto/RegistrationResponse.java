package com.example.Automated.Task.Management.dto;

import com.example.Automated.Task.Management.Model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationResponse {
    private String message;
    private Users user;

}

