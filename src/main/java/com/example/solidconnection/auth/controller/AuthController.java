package com.example.solidconnection.auth.controller;

import com.example.solidconnection.auth.dto.KakaoCodeDto;
import com.example.solidconnection.auth.dto.KakaoOauthResponseDto;
import com.example.solidconnection.auth.service.KakaoOAuthService;
import com.example.solidconnection.custom.response.CustomResponse;
import com.example.solidconnection.custom.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final KakaoOAuthService kakaoOAuthService;

    @PostMapping("/kakao")
    public CustomResponse signUp(@RequestBody KakaoCodeDto kakaoCodeDto) {
        KakaoOauthResponseDto kakaoOauthResponseDto = kakaoOAuthService.processOauth(kakaoCodeDto.getCode());
        return new DataResponse<>(kakaoOauthResponseDto);
    }
}
