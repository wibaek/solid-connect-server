package com.example.solidconnection.e2e;

import com.example.solidconnection.auth.service.TokenProvider;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.custom.response.ErrorResponse;
import com.example.solidconnection.siteuser.domain.SiteUser;
import com.example.solidconnection.siteuser.dto.MyPageUpdateResponse;
import com.example.solidconnection.siteuser.dto.NicknameUpdateRequest;
import com.example.solidconnection.siteuser.dto.NicknameUpdateResponse;
import com.example.solidconnection.siteuser.repository.SiteUserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.example.solidconnection.custom.exception.ErrorCode.CAN_NOT_CHANGE_NICKNAME_YET;
import static com.example.solidconnection.custom.exception.ErrorCode.NICKNAME_ALREADY_EXISTED;
import static com.example.solidconnection.e2e.DynamicFixture.createSiteUserByEmail;
import static com.example.solidconnection.siteuser.service.SiteUserService.MIN_DAYS_BETWEEN_NICKNAME_CHANGES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("마이페이지 수정 테스트")
class MyPageUpdateTest extends BaseEndToEndTest {

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private TokenProvider tokenProvider;

    private String accessToken;

    private SiteUser siteUser;

    private final String email = "email@email.com";

    @BeforeEach
    public void setUpUserAndToken() {
        // setUp - 회원 정보 저장
        siteUser = createSiteUserByEmail(email);
        siteUserRepository.save(siteUser);

        // setUp - 엑세스 토큰 생성과 리프레시 토큰 생성 및 저장
        accessToken = tokenProvider.generateToken(email, TokenType.ACCESS);
        String refreshToken = tokenProvider.generateToken(email, TokenType.REFRESH);
        tokenProvider.saveToken(refreshToken, TokenType.REFRESH);
    }

    @Test
    void 수정을_위해_수정_전_정보를_조회한다() {
        // request - 요청
        MyPageUpdateResponse myPageUpdateResponse = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .log().all()
                .get("/my-page/update")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MyPageUpdateResponse.class);

        SiteUser savedSiteUser = siteUserRepository.getByEmail(email);
        assertAll("불러온 마이 페이지 정보가 DB의 정보와 일치한다.",
                () -> assertThat(myPageUpdateResponse.nickname()).isEqualTo(savedSiteUser.getNickname()),
                () -> assertThat(myPageUpdateResponse.profileImageUrl()).isEqualTo(savedSiteUser.getProfileImageUrl()));
    }

    @Test
    void 닉네임을_수정한다() {
        // request - body 생성 및 요청
        NicknameUpdateRequest nicknameUpdateRequest = new NicknameUpdateRequest("newNickname");
        NicknameUpdateResponse nicknameUpdateResponse = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .log().all()
                .body(nicknameUpdateRequest)
                .contentType("application/json")
                .patch("/my-page/update/nickname")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(NicknameUpdateResponse.class);

        SiteUser savedSiteUser = siteUserRepository.getByEmail(email);
        assertAll("마이 페이지 정보가 수정된다.",
                () -> assertThat(nicknameUpdateResponse.nickname()).isEqualTo(savedSiteUser.getNickname()));
    }

    @Test
    void 닉네임을_수정할_때_닉네임이_중복된다면_예외_응답을_반환한다() {
        // setUp - 같은 닉네임을 갖는 다른 회원 정보 저장
        SiteUser existUser = createSiteUserByEmail("existUser");
        String duplicateNickname = "duplicateNickname";
        existUser.setNickname(duplicateNickname);
        siteUserRepository.save(existUser);

        // request - body 생성 및 요청
        NicknameUpdateRequest nicknameUpdateRequest = new NicknameUpdateRequest("duplicateNickname");
        ErrorResponse response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .log().all()
                .body(nicknameUpdateRequest)
                .contentType("application/json")
                .patch("/my-page/update/nickname")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value())
                .extract().as(ErrorResponse.class);

        assertThat(response.message())
                .isEqualTo(NICKNAME_ALREADY_EXISTED.getMessage());
    }

    @Test
    void 닉네임을_수정할_때_닉네임_변경_가능_기한이_지나지않았다면_예외_응답을_반환한다() {
        // setUp - 회원 정보 저장 (닉네임 변경 가능 시간이 되기 1분 전)
        LocalDateTime nicknameModifiedAt = LocalDateTime.now()
                .minusDays(MIN_DAYS_BETWEEN_NICKNAME_CHANGES)
                .plusMinutes(1);
        siteUser.setNicknameModifiedAt(nicknameModifiedAt);
        siteUserRepository.save(siteUser);

        // request - body 생성 및 요청
        NicknameUpdateRequest nicknameUpdateRequest = new NicknameUpdateRequest("newNickname");
        ErrorResponse response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .log().all()
                .body(nicknameUpdateRequest)
                .contentType("application/json")
                .patch("/my-page/update/nickname")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        assertThat(response.message())
                .contains(CAN_NOT_CHANGE_NICKNAME_YET.getMessage());
    }
}
