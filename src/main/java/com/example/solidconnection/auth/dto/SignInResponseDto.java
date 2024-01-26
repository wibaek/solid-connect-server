package com.example.solidconnection.auth.dto;

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
