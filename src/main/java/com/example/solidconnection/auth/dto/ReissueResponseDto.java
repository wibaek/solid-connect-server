package com.example.solidconnection.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReissueResponseDto {
    private String accessToken;
}
