package com.example.Automated.Task.Management.controllers;

import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.impl.PasswordResetServiceImpl;
import com.example.Automated.Task.Management.Services.impl.UserServiceImpl;
import com.example.Automated.Task.Management.dto.ChangeEmailRequest;
import com.example.Automated.Task.Management.dto.ChangePasswordRequest;
import com.example.Automated.Task.Management.dto.PasswordResetDTO;
import com.example.Automated.Task.Management.dto.PasswordResetRequest;
import com.example.Automated.Task.Management.exception.InvalidTokenException;
import com.example.Automated.Task.Management.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    private final PasswordResetServiceImpl passwordResetService;

    public UserController(UserServiceImpl userService, PasswordResetServiceImpl passwordResetService) {
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    // Endpoint to reset or change password
    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changesPassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        String newPassword = changePasswordRequest.getNewPassword();
        String currentPassword = changePasswordRequest.getCurrentPassword();
         userService.changePassword(id, currentPassword, newPassword);
         return ResponseEntity.ok("Password changed Successfully");
    }

    // Endpoint to change email
    @PutMapping("/{id}/change-email")
    public ResponseEntity<String> changeEmail(
            @PathVariable Long id,
            @RequestBody ChangeEmailRequest changeEmailRequest) {
        String newEmail = changeEmailRequest.getNewEmail();
        String currentEmail = changeEmailRequest.getCurrentEmail();
        userService.changeEmail(id, currentEmail, newEmail);
        return ResponseEntity.ok("Email changed Successfully");
    }


    @PostMapping("/password-reset-request")
        public ResponseEntity<String> requestPasswordReset(@RequestBody PasswordResetRequest request) {
            try {
                passwordResetService.sendResetToken(request.getEmail());
                return ResponseEntity.ok("Password reset token sent.");
            } catch (UserNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
            }
        }


    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestBody PasswordResetDTO resetDTO) {
        try {
            passwordResetService.resetPassword(resetDTO.getEmail(), resetDTO.getNewPassword(), token);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (InvalidTokenException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return  userService.getAllUsers();
    }
}
