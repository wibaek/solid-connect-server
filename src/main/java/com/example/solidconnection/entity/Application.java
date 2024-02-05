package com.example.solidconnection.entity;

import com.example.solidconnection.type.LanguageTestType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private LanguageTestType languageTestType;

    @Column(nullable = false)
    private String languageTestScore;

    @Column(nullable = false, length = 500)
    private String languageTestReportUrl;

    @Column(nullable = false)
    private Float gpa;

    @Column(nullable = false, length = 500)
    private String gpaReportUrl;

    @Column(nullable = false, length = 50)
    private String verifyStatus;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "first_choice_univ_id")
    private University firstChoiceUniversity;

    @ManyToOne
    @JoinColumn(name = "second_choice_univ_id")
    private University secondChoiceUniversity;

    @ManyToOne
    @JoinColumn(name = "site_user_id")
    private SiteUser siteUser;
}
