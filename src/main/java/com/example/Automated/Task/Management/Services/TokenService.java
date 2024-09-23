package com.example.Automated.Task.Management.Services;

import com.example.Automated.Task.Management.Model.Users;

public interface TokenService {
    String createResetToken(Users user);
    boolean isTokenValid(String token, Users user);
}
