package com.example.solidconnection.type;

public enum ApplicationStatusResponse {
    NOT_SUBMITTED, // 어떤 것도 제출하지 않음
    COLLEGE_SUBMITTED, // 지망 대학만 제출
    SCORE_SUBMITTED, // 성적만 제출
    SUBMITTED_PENDING, // 성적 인증 대기 중
    SUBMITTED_REJECTED, // 성적 인증 승인 완료
    SUBMITTED_APPROVED // 성적 인증 반려
}
