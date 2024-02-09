package com.example.solidconnection.home.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalHomeInfoDto {
    private List<RecommendedUniversityDto> recommendedUniversities;
}
