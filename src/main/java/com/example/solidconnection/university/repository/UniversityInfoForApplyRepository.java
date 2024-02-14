package com.example.solidconnection.university.repository;

import com.example.solidconnection.entity.University;
import com.example.solidconnection.entity.UniversityInfoForApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityInfoForApplyRepository extends JpaRepository<UniversityInfoForApply, Long> {
    Optional<UniversityInfoForApply> findByUniversityAndTerm(University university, String term);
    Optional<UniversityInfoForApply> findByUniversity_KoreanNameAndTerm(String koreanName, String term);
    Optional<UniversityInfoForApply> findByIdAndTerm(Long id, String term);
}