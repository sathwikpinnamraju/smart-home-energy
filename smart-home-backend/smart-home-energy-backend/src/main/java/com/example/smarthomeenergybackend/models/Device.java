package com.example.smarthomeenergybackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceName;

    @Enumerated(EnumType.STRING)  // ✅ Store enum as String in DB
    @Column(nullable = false)
    private DeviceStatus status = DeviceStatus.OFFLINE; // ✅ Default status

    @Column(nullable = false)
    private Integer homeId;

    @Column(nullable = false)
    private Integer householdSize;

    @Column(nullable = false)
    private double energyUsage = 0.0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnergyUsage> energyUsages;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}




