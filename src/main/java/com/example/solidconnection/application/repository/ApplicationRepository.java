package com.example.solidconnection.application.repository;

import com.example.solidconnection.entity.Application;
import com.example.solidconnection.entity.UniversityInfoForApply;
import com.example.solidconnection.type.VerifyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsBySiteUser_Email(String email);
    boolean existsByNicknameForApply(String nicknameForApply);
    Optional<Application> findBySiteUser_Email(String email);
    List<Application> findAllByFirstChoiceUniversityAndVerifyStatus(UniversityInfoForApply firstChoiceUniversity, VerifyStatus verifyStatus);
    List<Application> findAllBySecondChoiceUniversityAndVerifyStatus(UniversityInfoForApply secondChoiceUniversity, VerifyStatus verifyStatus);
}
