package com.example.solidconnection.entity;

import com.example.solidconnection.type.PreparationStatus;
import com.example.solidconnection.type.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PreparationStatus preparationStage;

    @Column(length = 500)
    private String profileImageUrl;

    private LocalDateTime nicknameModifiedAt;

    private LocalDateTime quitedAt;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role role;

    // 연관관계
    @OneToMany(mappedBy = "siteUser")
    private Set<InterestedRegion> interestedRegions;

    @OneToMany(mappedBy = "siteUser")
    private Set<InterestedCountry> interestedCountries;

    @OneToMany(mappedBy = "siteUser")
    private Set<Application> applications;

    @OneToMany(mappedBy = "siteUser")
    private Set<WishUniversity> wishUniversities;
}
