package com.example.smarthomeenergybackend.controllers;

import com.example.smarthomeenergybackend.models.EnergyUsage;
import com.example.smarthomeenergybackend.repositories.EnergyUsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/energy-usage")
@CrossOrigin(origins = "*")
public class EnergyUsageController {

    private static final Logger logger = LoggerFactory.getLogger(EnergyUsageController.class);
    private final EnergyUsageRepository energyUsageRepository;

    public EnergyUsageController(EnergyUsageRepository energyUsageRepository) {
        this.energyUsageRepository = energyUsageRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // ✅ Ensure correct role prefix
    public ResponseEntity<List<Map<String, Object>>> getEnergyUsage() {
        try {
            List<Map<String, Object>> energyData = energyUsageRepository.findAll().stream()
                    .filter(usage -> usage.getDevice() != null) // ✅ Prevent NullPointerException
                    .map(usage -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("device_id", usage.getDevice().getId());
                        data.put("device_name", usage.getDevice().getDeviceName());
                        data.put("usage", usage.getEnergyConsumedKwh() + " kWh");
                        return data;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(energyData); // ✅ Return empty list [] instead of NO_CONTENT
        } catch (Exception e) {
            logger.error("Error retrieving energy usage data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(Map.of("message", "Failed to retrieve energy usage data.")));
        }
    }
}














