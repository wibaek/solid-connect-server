package com.example.solidconnection.siteuser.dto;

import com.example.solidconnection.entity.SiteUser;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.solidconnection.constants.validMessage.NICKNAME_NOT_BLANK;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyPageUpdateDto {
    @NotBlank(message = NICKNAME_NOT_BLANK)
    private String nickname;
    private String profileImageUrl;

    public static MyPageUpdateDto fromEntity(SiteUser siteUser){
        return MyPageUpdateDto.builder()
                .nickname(siteUser.getNickname())
                .profileImageUrl(siteUser.getProfileImageUrl())
                .build();
    }
}
