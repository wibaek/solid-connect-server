package com.example.solidconnection.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UniversityApplicantsDto {
    private String koreanName;
    private int studentCapacity;
    private String region;
    private String country;
    private List<ApplicantDto> applicants;
}
