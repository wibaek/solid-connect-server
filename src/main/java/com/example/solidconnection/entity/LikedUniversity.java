package com.example.solidconnection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedUniversity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "university_info_for_apply_id")
    private UniversityInfoForApply universityInfoForApply;

    @ManyToOne
    @JoinColumn(name = "site_user_id")
    private SiteUser siteUser;
}