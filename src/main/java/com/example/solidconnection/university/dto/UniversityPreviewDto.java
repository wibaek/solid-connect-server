package com.example.solidconnection.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityPreviewDto {
    private long id;
    private String koreanName;
    private String region;
    private String country;
    private String logoImageUrl;
    private int studentCapacity;
    private List<LanguageRequirementDto> languageRequirements;
}