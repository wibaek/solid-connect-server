package com.example.solidconnection.entity;

import com.example.solidconnection.type.ExchangeSemester;
import com.example.solidconnection.type.TuitionFeePaymentType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String koreanName;

    @Column(nullable = false, length = 100)
    private String englishName;

    @Column(nullable = false, length = 100)
    private String internalName;

    @Column(nullable = false)
    private Integer recruitNumber;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TuitionFeePaymentType tuitionFeePaymentType;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ExchangeSemester exchangeSemester;

    @Column(length = 1000)
    private String detailsForLanguage;

    @Column(length = 1000)
    private String detailsForApply;

    @Column(length = 1000)
    private String detailsForMajor;

    @Column(length = 1000)
    private String detailsForAccommodation;

    @Column(length = 500)
    private String homepageUrl;

    @Column(length = 500)
    private String englishCourseUrl;

    @Column(length = 500)
    private String accommodationUrl;

    @Column(length = 500)
    private String details;

    @Column(nullable = false, length = 500)
    private String logoImageUrl;

    @Column(nullable = false, length = 500)
    private String backgroundImageUrl;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "country_code")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "region_code")
    private Region region;

    @OneToMany(mappedBy = "university")
    private Set<LanguageRequirement> languageRequirements;

    @OneToMany(mappedBy = "university")
    private Set<GpaRequirement> gpaRequirements;

    @OneToMany(mappedBy = "firstChoiceUniversity")
    private Set<Application> firstChoiceApplications;

    @OneToMany(mappedBy = "secondChoiceUniversity")
    private Set<Application> secondChoiceApplications;

    @OneToMany(mappedBy = "university")
    private Set<WishUniversity> wishUniversities;
}
