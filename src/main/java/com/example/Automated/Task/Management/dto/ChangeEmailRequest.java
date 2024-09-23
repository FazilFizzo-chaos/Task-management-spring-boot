package com.example.Automated.Task.Management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEmailRequest {
    private String currentEmail;
    private String newEmail;
}
