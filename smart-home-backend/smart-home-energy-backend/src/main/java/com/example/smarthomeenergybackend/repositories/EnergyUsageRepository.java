package com.example.smarthomeenergybackend.repositories;

import com.example.smarthomeenergybackend.models.EnergyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsage, Long> {

    // ✅ Fixed JPQL Query - Uses device ID instead of entire object
    @Query("SELECT COALESCE(SUM(e.energyConsumedKwh), 0) FROM EnergyUsage e WHERE e.device.id = :deviceId")
    Double getTotalEnergyConsumedByDevice(@Param("deviceId") Long deviceId);
}



