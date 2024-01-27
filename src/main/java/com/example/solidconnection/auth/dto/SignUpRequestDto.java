package com.example.solidconnection.auth.dto;

import com.example.solidconnection.type.Gender;
import com.example.solidconnection.type.PreparationStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {
    private String kakaoOauthToken;
    private List<String> interestedRegions;
    private List<String> interestedCountries;
    private PreparationStatus preparationStatus;
    private String nickname;
    private String profileImageUrl;
    private Gender gender;
    private String birth;
}
