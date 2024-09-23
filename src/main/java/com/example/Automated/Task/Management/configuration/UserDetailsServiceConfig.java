package com.example.Automated.Task.Management.configuration;

import com.example.Automated.Task.Management.Services.RoleService;
import com.example.Automated.Task.Management.Services.impl.UserServiceImpl;
import com.example.Automated.Task.Management.repository.RoleRepository;
import com.example.Automated.Task.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserDetailsServiceConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public UserDetailsServiceConfig(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService, BCryptPasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl(roleRepository, userRepository,  roleService, bcryptEncoder);
    }
}

