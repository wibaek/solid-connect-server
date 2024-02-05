package com.example.solidconnection.entity;

import com.example.solidconnection.type.LanguageTestType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class LanguageRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private LanguageTestType languageTestType;

    @Column(nullable = false)
    private String minScore;

    // 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_info_for_apply_id")
    private UniversityInfoForApply universityInfoForApply;
}
