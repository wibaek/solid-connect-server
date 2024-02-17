package com.example.solidconnection.university.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDetailDto {
    private long id;
    @Setter
    private boolean isLiked = false;
    private String term;
    private String koreanName;
    private String englishName;
    private String formatName;
    private String region;
    private String country;
    private String homepageUrl;
    private String logoImageUrl;
    private String backgroundImageUrl;
    private String detailsForLocal;
    private int studentCapacity;
    private String tuitionFeeType;
    private String semesterAvailableForDispatch;
    private List<LanguageRequirementDto> languageRequirements;
    private String detailsForLanguage;
    private String gpaRequirement;
    private String gpaRequirementCriteria;
    private String semesterRequirement;
    private String detailsForApply;
    private String detailsForMajor;
    private String detailsForAccommodation;
    private String detailsForEnglishCourse;
    private String details;
    private String accommodationUrl;
    private String englishCourseUrl;
}