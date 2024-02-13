package com.example.solidconnection.application.repository;

import com.example.solidconnection.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsBySiteUser_Email(String email);
    boolean existsByNicknameForApply(String nicknameForApply);
    Optional<Application> findBySiteUser_Email(String email);
}
