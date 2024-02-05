package com.example.solidconnection.auth.dto;

import com.example.solidconnection.type.Gender;
import com.example.solidconnection.type.PreparationStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

import static com.example.solidconnection.constants.validMessage.NICKNAME_NOT_BLANK;

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
    @NotBlank(message = NICKNAME_NOT_BLANK)
    private String nickname;
    private String profileImageUrl;
    private Gender gender;
    private String birth;
}
