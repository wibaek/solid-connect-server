package com.example.solidconnection.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDto {
    private String accessToken;
    private String refreshToken;
}
