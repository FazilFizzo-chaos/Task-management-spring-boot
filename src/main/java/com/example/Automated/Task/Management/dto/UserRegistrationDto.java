package com.example.Automated.Task.Management.dto;

import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Model.Users;
import jakarta.validation.constraints.*;

import java.util.Set;

public class UserRegistrationDto {

    @NotBlank(message = "name should not be blank")
    private String username;

    @NotBlank(message = "email should not be blank")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "password should not be blank")
    @Size(min = 8, max = 20, message = "Password must be at least 8 character long")
    @Pattern(regexp = ".*[A-Z].*", message = "password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*",message = "password should contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "password should contain at least a number")
    @Pattern(regexp = ".*[@#$%^&+=].*", message = "password should contain at least one special character")
    private String password;

//    private String role;


    public UserRegistrationDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Users getUserFromDto(){
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

}

