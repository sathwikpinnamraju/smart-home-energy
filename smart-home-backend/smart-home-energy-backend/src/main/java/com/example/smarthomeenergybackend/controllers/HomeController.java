package com.example.smarthomeenergybackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")  // Base path for all endpoints in this controller
public class HomeController {

    @GetMapping("/home")  // Full path: /api/home
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the Smart Home Energy Monitoring System!");
        response.put("status", "UP");  // ✅ Added API status indicator

        return ResponseEntity.ok(response);
    }
}





