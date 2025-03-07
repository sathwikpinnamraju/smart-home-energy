package com.example.smarthomeenergybackend.dtos;

import com.example.smarthomeenergybackend.models.DeviceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private Long id;

    @NotNull(message = "Device name is required.")
    private String deviceName;

    @NotNull(message = "Home ID is required.")
    @Positive(message = "Home ID must be a positive number.")
    private Integer homeId;

    @NotNull(message = "Household size is required.")
    @Positive(message = "Household size must be a positive number.")
    private Integer householdSize;

    private Double energyUsage = 0.0; // Default value

    private DeviceStatus status = DeviceStatus.OFFLINE; // ✅ Use enum instead of string
}


