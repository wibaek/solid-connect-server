package com.example.solidconnection.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GpaRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String scale;

    @Column(nullable = false)
    private String minGpa;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "university_info_for_apply_id")
    private UniversityInfoForApply universityInfoForApply;
}
