package com.example.Automated.Task.Management.Services.impl;

import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.PasswordResetService;
import com.example.Automated.Task.Management.exception.InvalidTokenException;
import com.example.Automated.Task.Management.exception.UserNotFoundException;
import com.example.Automated.Task.Management.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "PasswordResetService")
public class PasswordResetServiceImpl implements PasswordResetService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenServiceImpl tokenService;

    private final UserRepository userRepository;

    public PasswordResetServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, TokenServiceImpl tokenService, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    public void sendResetToken(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        String token = tokenService.createResetToken(user);
        // Send the token to the user via email or SMS
    }

    public void resetPassword(String email, String newPassword, String token) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!tokenService.isTokenValid(token, user)) {
            throw new InvalidTokenException("Invalid or expired reset token.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
