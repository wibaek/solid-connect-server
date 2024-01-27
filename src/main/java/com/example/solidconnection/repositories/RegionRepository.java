package com.example.solidconnection.repositories;

import com.example.solidconnection.entity.Region;
import com.example.solidconnection.type.RegionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByRegionCode(RegionCode regionCode);
}
