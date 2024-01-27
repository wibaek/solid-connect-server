package com.example.solidconnection.entity;

import com.example.solidconnection.type.Gender;
import com.example.solidconnection.type.PreparationStatus;
import com.example.solidconnection.type.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(length = 500)
    private String profileImageUrl;

    @Column(nullable = false, length = 20)
    private String birth;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PreparationStatus preparationStage;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime nicknameModifiedAt;

    private LocalDateTime quitedAt;

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
