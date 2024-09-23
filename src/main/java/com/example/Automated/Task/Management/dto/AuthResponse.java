package com.example.Automated.Task.Management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    String token;
    String username;
    String email;
    String subject;
    String scope;
}
