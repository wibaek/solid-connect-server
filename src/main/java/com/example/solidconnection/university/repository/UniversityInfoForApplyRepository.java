package com.example.solidconnection.university.repository;

import com.example.solidconnection.entity.University;
import com.example.solidconnection.entity.UniversityInfoForApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityInfoForApplyRepository extends JpaRepository<UniversityInfoForApply, Long> {
    Optional<UniversityInfoForApply> findByUniversity(University university);
}