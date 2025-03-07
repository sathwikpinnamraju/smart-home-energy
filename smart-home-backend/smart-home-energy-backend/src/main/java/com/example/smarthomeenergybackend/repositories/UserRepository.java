package com.example.smarthomeenergybackend.repositories;

import com.example.smarthomeenergybackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // ✅ Find user by username

    Optional<User> findByEmail(String email); // ✅ Find user by email

    boolean existsByUsername(String username); // ✅ Check if a username exists

    boolean existsByEmail(String email); // ✅ Check if an email exists
}

