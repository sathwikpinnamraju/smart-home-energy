package com.example.smarthomeenergybackend.controllers;

import com.example.smarthomeenergybackend.models.User;
import com.example.smarthomeenergybackend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Register a new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        try {
            // Normalize username & email (to lower case for consistency)
            String normalizedUsername = user.getUsername().toLowerCase();
            String normalizedEmail = user.getEmail().toLowerCase();

            // Check if username or email already exists
            if (userRepository.findByUsername(normalizedUsername).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Username already exists."));
            }

            if (userRepository.findByUsername(normalizedEmail).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Email already registered."));
            }

            // Standardize role as "ROLE_USER" or "ROLE_ADMIN"
            String role = user.getRole().toUpperCase();
            if (!role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Invalid role. Allowed roles: ROLE_USER, ROLE_ADMIN."));
            }

            // Hash password and save user
            user.setUsername(normalizedUsername);
            user.setEmail(normalizedEmail);
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "User registered successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to register user.", "error", e.getMessage()));
        }
    }

    // ✅ Fetch user details
    @GetMapping("/{username}")
    public ResponseEntity<Map<String, String>> getUser(@PathVariable String username) {
        Optional<User> user = userRepository.findByUsername(username.toLowerCase());

        return user.map(foundUser -> ResponseEntity.ok(Map.of(
                        "username", foundUser.getUsername(),
                        "email", foundUser.getEmail(),
                        "role", foundUser.getRole()
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found.")));
    }

    // ✅ List all users (Optional)
    @GetMapping
    public ResponseEntity<List<Map<String, String>>> getAllUsers() {
        List<Map<String, String>> users = userRepository.findAll().stream()
                .map(user -> Map.of(
                        "username", user.getUsername(),
                        "email", user.getEmail(),
                        "role", user.getRole()
                ))
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(users);
    }
}






