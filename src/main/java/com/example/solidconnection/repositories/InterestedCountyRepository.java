package com.example.solidconnection.repositories;

import com.example.solidconnection.entity.InterestedCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestedCountyRepository extends JpaRepository<InterestedCountry, Long> {
    List<InterestedCountry> findAllBySiteUser_Email(String email);
}
