package com.example.solidconnection.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirstAccessResponseDto extends KakaoOauthResponseDto {
    private boolean registered;
    private String nickname;
    private String email;
    private String profileImageUrl;
    private String kakaoOauthToken;

    public static FirstAccessResponseDto fromKakaoUserInfo(KakaoUserInfoDto kakaoUserInfoDto, String kakaoOauthToken){
        return FirstAccessResponseDto.builder()
                .registered(false)
                .email(kakaoUserInfoDto.getKakaoAccount().getEmail())
                .profileImageUrl(kakaoUserInfoDto.getKakaoAccount().getProfile().getProfileImageUrl())
                .nickname(kakaoUserInfoDto.getKakaoAccount().getProfile().getNickname())
                .kakaoOauthToken(kakaoOauthToken)
                .build();
    }
}
