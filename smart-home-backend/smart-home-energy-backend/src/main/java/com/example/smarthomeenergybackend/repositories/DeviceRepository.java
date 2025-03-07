package com.example.smarthomeenergybackend.repositories;

import com.example.smarthomeenergybackend.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByDeviceName(String deviceName);

    List<Device> findByStatus(String status); // ✅ Ensure this method accepts String, not DeviceStatus

    List<Device> findByHomeId(Integer homeId);
}




