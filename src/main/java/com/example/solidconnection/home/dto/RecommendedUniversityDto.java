package com.example.solidconnection.home.dto;

import com.example.solidconnection.entity.UniversityInfoForApply;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendedUniversityDto {
    private long id;
    private String koreanName;
    private String backgroundImgUrl;

    public static RecommendedUniversityDto fromEntity(UniversityInfoForApply universityInfoForApply){
        return RecommendedUniversityDto.builder()
                .id(universityInfoForApply.getId())
                .backgroundImgUrl(universityInfoForApply.getUniversity().getBackgroundImageUrl())
                .koreanName(universityInfoForApply.getUniversity().getKoreanName())
                .build();
    }
}
