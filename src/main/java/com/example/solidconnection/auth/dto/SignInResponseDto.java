package com.example.solidconnection.auth.dto;

import com.example.solidconnection.auth.dto.kakao.KakaoOauthResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponseDto extends KakaoOauthResponseDto {
    private boolean registered;
    private String accessToken;
    private String refreshToken;
}
