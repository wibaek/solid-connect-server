package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.dto.*;
import com.example.solidconnection.config.security.TokenProvider;
import com.example.solidconnection.config.security.TokenType;
import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.siteuser.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.solidconnection.custom.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final RestTemplate restTemplate;
    private final TokenProvider tokenProvider;
    private final SiteUserRepository siteUserRepository;

    @Value("${kakao.client_id}")
    private String clientId;
    @Value("${kakao.redirect_uri}")
    private String redirectUri;
    @Value("${kakao.token_url}")
    private String tokenUrl;
    @Value("${kakao.user_info_url}")
    private String userInfoUrl;

    public KakaoOauthResponseDto processOauth(String code) {
        String kakaoAccessToken = getKakaoAccessToken(code);
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(kakaoAccessToken);
        String email = kakaoUserInfoDto.getKakaoAccount().getEmail();
        boolean isAlreadyRegistered = siteUserRepository.existsByEmail(email);
        if (isAlreadyRegistered) {
            return kakaoSignIn(email);
        }
        String kakaoOauthToken = tokenProvider.generateToken(email, TokenType.KAKAO_OAUTH);
        return FirstAccessResponseDto.fromKakaoUserInfo(kakaoUserInfoDto, kakaoOauthToken);
    }

    private String getKakaoAccessToken(String code) {
        // 카카오 엑세스 토큰 요청
        ResponseEntity<KakaoTokenDto> response = restTemplate.exchange(
                buildTokenUri(code),
                HttpMethod.POST,
                null,
                KakaoTokenDto.class
        );

        // 응답 예외처리
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getAccessToken();
        } else {
            throw new CustomException(KAKAO_ACCESS_TOKEN_FAIL);
        }
    }

    // 카카오에게 엑세스 토큰 발급 요청하는 URI 생성
    private String buildTokenUri(String code) {
        return UriComponentsBuilder.fromHttpUrl(tokenUrl)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .toUriString();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) {
        // 카카오 엑세스 토큰을 헤더에 담은 HttpEntity
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // 사용자의 정보 요청
        ResponseEntity<KakaoUserInfoDto> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                entity,
                KakaoUserInfoDto.class
        );

        // 응답 예외처리
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new CustomException(KAKAO_USER_INFO_FAIL);
        }
    }

    private SignInResponseDto kakaoSignIn(String email) {
        siteUserRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        var accessToken = tokenProvider.generateToken(email, TokenType.ACCESS);
        var refreshToken = tokenProvider.saveToken(email, TokenType.REFRESH);
        return SignInResponseDto.builder()
                .registered(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
