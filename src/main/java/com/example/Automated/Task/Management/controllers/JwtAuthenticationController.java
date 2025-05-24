package com.example.Automated.Task.Management.controllers;

import com.example.Automated.Task.Management.Model.CustomUserDetails;
import com.example.Automated.Task.Management.dto.AuthResponse;
import com.example.Automated.Task.Management.Model.JwtTokenRequest;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.impl.UserServiceImpl;
import com.example.Automated.Task.Management.configuration.JwtTokenProvider;
import com.example.Automated.Task.Management.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    UserServiceImpl userService;

    private final BCryptPasswordEncoder bcryptEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationController(BCryptPasswordEncoder bcryptEncoder, JwtTokenProvider jwtTokenProvider,
                                       AuthenticationManager authenticationManager) {
        this.bcryptEncoder = bcryptEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    //method for authenticating user as jwtTokenRequest as parameter
    private Authentication authenticateUser(JwtTokenRequest jwtTokenRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        jwtTokenRequest.username(),
                        jwtTokenRequest.password());

        return authenticationManager.authenticate(authenticationToken);
    }

    //authenticating users here(jwt authentication token)
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> generateToken(
            @RequestBody JwtTokenRequest jwtTokenRequest) {
        var authentication = authenticateUser(jwtTokenRequest);

        var token = jwtTokenProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //Extract claims
        Map<String,Object> claims = jwtTokenProvider.extractClaims(token);


        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUsername(userDetails.getUsername());
        response.setEmail(((CustomUserDetails) userDetails).getEmail());
        response.setSubject(claims.get("sub").toString());
        response.setScope(claims.get("roles").toString());
        return ResponseEntity.ok(response);
    }

    //registering users here
    @PostMapping("/register")
    public Users saveUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
        return userService.save(userRegistrationDto);
    }
    
}

