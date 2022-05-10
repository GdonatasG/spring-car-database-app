package com.example.cardatabaseapp.login.repository;

import java.util.Optional;

import com.example.cardatabaseapp.login.model.ERole;
import com.example.cardatabaseapp.login.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}