package com.example.solidconnection.university.dto;

import com.example.solidconnection.entity.UniversityInfoForApply;
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

    public static UniversityPreviewDto fromEntity(UniversityInfoForApply universityInfoForApply) {
        return UniversityPreviewDto.builder()
                .id(universityInfoForApply.getId())
                .region(universityInfoForApply.getUniversity().getRegion().getCode().getKoreanName())
                .country(universityInfoForApply.getUniversity().getCountry().getCode().getKoreanName())
                .logoImageUrl(universityInfoForApply.getUniversity().getLogoImageUrl())
                .koreanName(universityInfoForApply.getUniversity().getKoreanName())
                .studentCapacity(universityInfoForApply.getStudentCapacity())
                .languageRequirements(universityInfoForApply.getLanguageRequirements().stream()
                        .map(LanguageRequirementDto::fromEntity)
                        .toList()
                )
                .build();
    }
}