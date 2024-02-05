package com.example.solidconnection.university.dto;

import com.example.solidconnection.entity.LanguageRequirement;
import com.example.solidconnection.type.LanguageTestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageRequirementDto {
    private LanguageTestType languageTestType;
    private String minScore;

    public static LanguageRequirementDto fromEntity(LanguageRequirement languageRequirement){
        return LanguageRequirementDto.builder()
                .languageTestType(languageRequirement.getLanguageTestType())
                .minScore(languageRequirement.getMinScore())
                .build();
    }
}