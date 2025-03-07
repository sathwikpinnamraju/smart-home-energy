package com.example.smarthomeenergybackend.controllers;

import com.example.smarthomeenergybackend.dtos.DeviceDTO;
import com.example.smarthomeenergybackend.models.Device;
import com.example.smarthomeenergybackend.models.DeviceStatus;
import com.example.smarthomeenergybackend.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "*")
@Validated
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    private final DeviceRepository deviceRepository;

    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Register a new device
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> registerDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        try {
            // Check if device with the same name already exists
            Optional<Device> existingDevice = deviceRepository.findByDeviceName(deviceDTO.getDeviceName());
            if (existingDevice.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Device with this name already exists."));
            }

            Device newDevice = new Device();
            newDevice.setDeviceName(deviceDTO.getDeviceName());
            newDevice.setHomeId(deviceDTO.getHomeId());
            newDevice.setHouseholdSize(deviceDTO.getHouseholdSize());
            newDevice.setEnergyUsage(deviceDTO.getEnergyUsage() != null ? deviceDTO.getEnergyUsage() : 0.0);
            newDevice.setStatus(deviceDTO.getStatus() != null ? deviceDTO.getStatus() : DeviceStatus.OFFLINE); // ✅ Fix enum issue

            Device savedDevice = deviceRepository.save(newDevice);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Device registered successfully.", "device_id", String.valueOf(savedDevice.getId())));
        } catch (Exception e) {
            logger.error("Error occurred while saving device: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Failed to create the device."));
        }
    }

    /**
     * Update an existing device
     */
    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        try {
            if (deviceDTO.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Device ID is required."));
            }

            Optional<Device> optionalDevice = deviceRepository.findById(deviceDTO.getId());
            if (optionalDevice.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Device not found."));
            }

            Device existingDevice = optionalDevice.get();

            // Check if updating to a deviceName that already exists (excluding itself)
            if (deviceDTO.getDeviceName() != null && !deviceDTO.getDeviceName().equals(existingDevice.getDeviceName())) {
                Optional<Device> deviceWithSameName = deviceRepository.findByDeviceName(deviceDTO.getDeviceName());
                if (deviceWithSameName.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(Map.of("message", "Another device with this name already exists."));
                }
                existingDevice.setDeviceName(deviceDTO.getDeviceName());
            }

            existingDevice.setStatus(deviceDTO.getStatus() != null ? deviceDTO.getStatus() : existingDevice.getStatus()); // ✅ Fix enum handling
            existingDevice.setHomeId(deviceDTO.getHomeId() != null ? deviceDTO.getHomeId() : existingDevice.getHomeId());
            existingDevice.setHouseholdSize(deviceDTO.getHouseholdSize() != null ? deviceDTO.getHouseholdSize() : existingDevice.getHouseholdSize());

            deviceRepository.save(existingDevice);
            return ResponseEntity.ok(Map.of("message", "Device updated successfully."));
        } catch (Exception e) {
            logger.error("Error occurred while updating device: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Failed to update the device."));
        }
    }

    /**
     * Get all devices
     */
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        if (devices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(devices);
    }

    /**
     * Get devices by status (e.g., ONLINE, OFFLINE)
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Device>> getDevicesByStatus(@PathVariable String status) {
        try {
            DeviceStatus deviceStatus = DeviceStatus.valueOf(status.toUpperCase()); // ✅ Convert to Enum
            List<Device> devices = deviceRepository.findByStatus(deviceStatus.name()); // ✅ Convert Enum back to String

            if (devices.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(devices);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.emptyList());
        }
    }


    /**
     * Delete a device by ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteDevice(@PathVariable Long id) {
        try {
            Optional<Device> device = deviceRepository.findById(id);
            if (device.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Device not found."));
            }
            deviceRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Device deleted successfully."));
        } catch (Exception e) {
            logger.error("Error occurred while deleting device: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Failed to delete the device."));
        }
    }
}












