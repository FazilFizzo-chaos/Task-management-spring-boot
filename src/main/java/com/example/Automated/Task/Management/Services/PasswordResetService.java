package com.example.Automated.Task.Management.Services;

public interface PasswordResetService {
    void sendResetToken(String email);
    void resetPassword(String email, String newPassword, String token);
}
