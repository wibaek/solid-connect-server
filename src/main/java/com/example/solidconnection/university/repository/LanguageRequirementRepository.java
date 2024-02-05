package com.example.solidconnection.university.repository;

import com.example.solidconnection.entity.LanguageRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRequirementRepository extends JpaRepository<LanguageRequirement, Long> {
    List<LanguageRequirement> findAllByUniversityInfoForApply_Id(Long id);
}
