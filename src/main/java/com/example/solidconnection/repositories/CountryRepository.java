package com.example.solidconnection.repositories;

import com.example.solidconnection.entity.Country;
import com.example.solidconnection.type.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCountryCode(CountryCode countryCode);
}
