package com.example.Automated.Task.Management.Services.impl;

import com.example.Automated.Task.Management.Model.CustomUserDetails;
import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Model.Users;
import com.example.Automated.Task.Management.Services.RoleService;
import com.example.Automated.Task.Management.Services.UserService;
import com.example.Automated.Task.Management.dto.UserDTO;
import com.example.Automated.Task.Management.dto.UserRegistrationDto;
import com.example.Automated.Task.Management.exception.InvalidPasswordException;
import com.example.Automated.Task.Management.exception.ResourceNotFoundException;
import com.example.Automated.Task.Management.exception.UserNotFoundException;
import com.example.Automated.Task.Management.repository.RoleRepository;
import com.example.Automated.Task.Management.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository , RoleService roleService, BCryptPasswordEncoder bcryptEncoder) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    //loads user details from database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                getAuthority(user),
                user.getEmail()
        );
    }


    private Set<SimpleGrantedAuthority> getAuthority(Users user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

    public List<Users> findAll() {
        List<Users> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }


    @Override
    public Users findOne(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDTO> getEmployees() {
        Role employeeRole = roleRepository.findRoleByName("EMPLOYEE")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        List<Users> employees = userRepository.findByRole(employeeRole);
        return employees.stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public List<Users> getProjectManagers() {
        Role managerRole = roleRepository.findRoleByName("PROJECT_MANAGER").orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return userRepository.findByRole(managerRole);
    }

    public Users save(UserRegistrationDto user) {

        // Check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        Users savedUser = user.getUserFromDto();
        savedUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Set<Role> roleSet = new HashSet<>();

       //Fetch the role based on selected role
//        Role selectedRole = roleService.GetByName(user.getRole());
//        roleSet.add(selectedRole);


        if(savedUser.getEmail().split("@")[1].equals("admin.edu")){
            Role  role = roleService.GetByName("ADMIN");
            roleSet.add(role);
        }

        if(savedUser.getEmail().split("@")[1].equals("pm.edu")){
            Role  role = roleService.GetByName("PROJECT_MANAGER");
            roleSet.add(role);
        }

        if(savedUser.getEmail().split("@")[1].equals("employee.edu")){
            Role  role = roleService.GetByName("EMPLOYEE");
            roleSet.add(role);
        }

        savedUser.setRoles(roleSet);
        return userRepository.save(savedUser);
    }

    //service implementation for changing password for users
    @Override
    public void changePassword(Long userid, String currentPassword, String newPassword) {
        Users user = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if (!bcryptEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect.");
        }
        user.setPassword(bcryptEncoder.encode(newPassword));
        userRepository.save(user);
    }

    //service implementation for changing email for users
    @Override
    public void changeEmail(Long userid, String currentEmail, String newEmail) {
        Users user = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("User Not Found"));
             if(!user.getEmail().equals(currentEmail)) {
                 throw new IllegalArgumentException("current email does not exist");
             }

             if(!userRepository.findByEmail(newEmail).isPresent()) {
                 throw new IllegalArgumentException("The new email already exists");
             }
             user.setEmail(newEmail);
             userRepository.save(user);
    }


    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


    public Users updateUserRoles(Long userId, Set<Role> roles) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    //delete user
    public void deleteUser(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            // Detach user from roles
            user.getRoles().clear();
            userRepository.save(user);

            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }
}





