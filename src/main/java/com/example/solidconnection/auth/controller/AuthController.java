package com.example.solidconnection.auth.controller;

import com.example.solidconnection.auth.dto.SignUpRequestDto;
import com.example.solidconnection.auth.dto.SignUpResponseDto;
import com.example.solidconnection.auth.dto.kakao.KakaoCodeDto;
import com.example.solidconnection.auth.dto.kakao.KakaoOauthResponseDto;
import com.example.solidconnection.auth.service.AuthService;
import com.example.solidconnection.auth.service.KakaoOAuthService;
import com.example.solidconnection.custom.response.CustomResponse;
import com.example.solidconnection.custom.response.DataResponse;
import com.example.solidconnection.custom.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final KakaoOAuthService kakaoOAuthService;
    private final AuthService authService;

    @PostMapping("/kakao")
    public CustomResponse kakaoOauth(@RequestBody KakaoCodeDto kakaoCodeDto) {
        KakaoOauthResponseDto kakaoOauthResponseDto = kakaoOAuthService.processOauth(kakaoCodeDto.getCode());
        return new DataResponse<>(kakaoOauthResponseDto);
    }

    @PostMapping("/sign-up")
    public CustomResponse signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = authService.signUp(signUpRequestDto);
        return new DataResponse<>(signUpResponseDto);
    }

    @PostMapping("/sign-out")
    public CustomResponse signOut(Principal principal) {
        boolean status = authService.signOut(principal.getName());
        return new StatusResponse(status);
    }

    @PatchMapping("/quit")
    public CustomResponse quit(Principal principal) {
        boolean status = authService.quit(principal.getName());
        return new StatusResponse(status);
    }
}
