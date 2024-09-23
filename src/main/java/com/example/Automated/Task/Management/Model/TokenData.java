package com.example.Automated.Task.Management.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
public class TokenData {
    private final String email;
    private final Instant expiryDate;
}
