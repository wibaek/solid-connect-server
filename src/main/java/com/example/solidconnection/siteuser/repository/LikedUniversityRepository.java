package com.example.solidconnection.siteuser.repository;

import com.example.solidconnection.entity.LikedUniversity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedUniversityRepository extends JpaRepository<LikedUniversity, Long> {
    List<LikedUniversity> findAllBySiteUser_Email(String email);
    int countBySiteUser_Email(String email);
}
