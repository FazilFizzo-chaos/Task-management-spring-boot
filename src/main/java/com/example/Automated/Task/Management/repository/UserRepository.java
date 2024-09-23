package com.example.Automated.Task.Management.repository;

import com.example.Automated.Task.Management.Model.Role;
import com.example.Automated.Task.Management.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    @Query("SELECT u FROM Users u JOIN u.roles r WHERE r = :role")
    List<Users> findByRole(@Param("role") Role role);

  Optional<Users> findByEmail(String email);

}
