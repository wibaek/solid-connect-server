package com.example.solidconnection.application.dto;

import com.example.solidconnection.entity.Application;
import com.example.solidconnection.type.LanguageTestType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantDto {
    private String nicknameForApply;
    private float gpa;
    private LanguageTestType testType;
    private String testScore;
    private boolean isMine;

    public static ApplicantDto fromEntity(Application application, boolean isMine) {
        return ApplicantDto.builder()
                .nicknameForApply(application.getNicknameForApply())
                .gpa(application.getGpa())
                .testType(application.getLanguageTestType())
                .testScore(application.getLanguageTestScore())
                .isMine(isMine)
                .build();
    }
}
