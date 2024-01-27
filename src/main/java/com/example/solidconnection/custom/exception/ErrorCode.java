package com.example.solidconnection.custom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "토큰이 필요한 경로에 빈 토큰으로 요청했습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED.value(), "인증이 필요한 접근입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "회원 정보를 찾을 수 없습니다."),
    KAKAO_ACCESS_TOKEN_FAIL(HttpStatus.BAD_REQUEST.value(),"카카오 엑세스 토큰 발급에 실패했습니다."),
    KAKAO_USER_INFO_FAIL(HttpStatus.BAD_REQUEST.value(),"카카오 사용자 정보 조회에 실패했습니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(),"액세스 토큰이 만료되었습니다. 재발급 api를 호출해주세요."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(),"리프레시 토큰이 만료되었습니다. 다시 로그인을 진행해주세요."),
    KAKAO_AUTH_CODE_EXPIRED(HttpStatus.UNAUTHORIZED.value(),"카카오 인증 코드가 만료되었습니다. 카카오 로그인 후 다시 시도해주세요.")
    ;

    private final int code;
    private final String message;
}
