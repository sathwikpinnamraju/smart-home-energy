package com.example.smarthomeenergybackend.service;

import com.example.smarthomeenergybackend.models.User;
import com.example.smarthomeenergybackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // 🔹 Allow login with either `username` or `email`
        Optional<User> userOpt = userRepository.findByUsername(identifier.toLowerCase())
                .or(() -> userRepository.findByEmail(identifier.toLowerCase())); // 🔹 Try finding by email if not found by username

        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found: " + identifier));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(
                        user.getRole().startsWith("ROLE_") ? user.getRole() : "ROLE_" + user.getRole() // 🔹 Ensure role has "ROLE_" prefix
                )))
                .build();
    }
}



