package com.example.solidconnection.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationsDto {
    private List<UniversityApplicantsDto> firstChoice;
    private List<UniversityApplicantsDto> secondChoice;
}
