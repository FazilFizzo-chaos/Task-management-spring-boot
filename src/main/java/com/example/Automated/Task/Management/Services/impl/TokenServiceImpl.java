package com.example.Automated.Task.Management.Services.impl;

import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Model.TokenData;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.TokenService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service(value = "TokenService")
public class TokenServiceImpl implements TokenService {

    private final Map<String, TokenData> tokenStore = new HashMap<>();
    private final long tokenValidityInMillis = 3600000; // 1 hour validity

    //creating reset token
    @Override
    public String createResetToken(Users user) {
        String token = UUID.randomUUID().toString();
        TokenData tokenData = new TokenData(user.getEmail(), Instant.now().plusMillis(tokenValidityInMillis));
        tokenStore.put(token, tokenData);
        return token;
    }

    //checking if the token is valid
    @Override
    public boolean isTokenValid(String token, Users user) {
        TokenData tokenData = tokenStore.get(token);
        if (tokenData == null || !tokenData.getEmail().equals(user.getEmail())) {
            return false;
        }
        return Instant.now().isBefore(tokenData.getExpiryDate());
    }
}
//
//Set<Role> roleSet = new HashSet<>();


