package com.example.solidconnection.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class LikedUniversity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne
    @JoinColumn(name = "site_user_id")
    private SiteUser siteUser;
}