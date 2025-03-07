package com.example.smarthomeenergybackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "energy_usage")
@Getter
@Setter
@NoArgsConstructor  // ✅ Removes the need for explicit constructor
public class EnergyUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)  // ✅ Better cascade & performance
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "energy_consumed_kwh", nullable = false)
    private double energyConsumedKwh;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "recorded_at", nullable = false, updatable = false)
    private Date recordedAt = new Date();  // ✅ Default value (DB should handle it too)

    // Parameterized constructor
    public EnergyUsage(Device device, double energyConsumedKwh) {
        this.device = device;
        this.energyConsumedKwh = energyConsumedKwh;
    }
}



