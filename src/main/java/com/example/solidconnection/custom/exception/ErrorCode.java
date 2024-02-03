package com.example.solidconnection.custom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    REDIRECT_URI_MISMATCH(HttpStatus.BAD_REQUEST.value(), "리다이렉트 uri가 잘못되었습니다."),
    NOT_DEFINED_ERROR(HttpStatus.BAD_REQUEST.value(), "에러가 발생했습니다."),
    USER_ALREADY_SIGN_OUT(HttpStatus.UNAUTHORIZED.value(), "로그아웃 되었습니다."),
    USER_ALREADY_EXISTED(HttpStatus.CONFLICT.value(), "이미 존재하는 회원입니다."),
    JSON_PARSING_FAILED(HttpStatus.BAD_REQUEST.value(), "JSON 파싱 에러"),
    INVALID_REGION_NAME(HttpStatus.BAD_REQUEST.value(), "지원하지 않는 지역명입니다."),
    INVALID_COUNTRY_NAME(HttpStatus.BAD_REQUEST.value(), "지원하지 않는 국가명입니다."),
    INVALID_BIRTH_FORMAT(HttpStatus.BAD_REQUEST.value(), "잘못된 생년월일 형식입니다."),
    NICKNAME_ALREADY_EXISTED(HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "토큰이 필요한 경로에 빈 토큰으로 요청했습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED.value(), "인증이 필요한 접근입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "회원 정보를 찾을 수 없습니다."),
    INVALID_KAKAO_AUTH_CODE(HttpStatus.BAD_REQUEST.value(),"사용할 수 없는 카카오 인증 코드입니다. 카카오 인증 코드는 일회용이며, 인증 만료 시간은 10분입니다."),
    KAKAO_USER_INFO_FAIL(HttpStatus.BAD_REQUEST.value(),"카카오 사용자 정보 조회에 실패했습니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(),"액세스 토큰이 만료되었습니다. 재발급 api를 호출해주세요."),
    INVALID_KAKAO_TOKEN(HttpStatus.UNAUTHORIZED.value(),"사용할 수 없는 카카오 로그인 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(),"리프레시 토큰이 만료되었습니다. 다시 로그인을 진행해주세요."),
    ;

    private final int code;
    private final String message;
}
