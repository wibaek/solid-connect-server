package com.example.solidconnection.siteuser.dto;

import com.example.solidconnection.entity.SiteUser;
import com.example.solidconnection.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyPageDto {
    private String nickname;
    private String profileImageUrl;
    private Role role;
    private String birth;
    private int likedPostCount;
    private int likedMentorCount;
    private int likedUniversityCount;

    public static MyPageDto fromEntity(SiteUser siteUser, int likedUniversityCount){
        return MyPageDto.builder()
                .nickname(siteUser.getNickname())
                .profileImageUrl(siteUser.getProfileImageUrl())
                .role(siteUser.getRole())
                .birth(siteUser.getBirth())
                .likedUniversityCount(likedUniversityCount)
                .likedMentorCount(0) // TODO: 멘토 기능 생기면 업데이트 필요
                .likedPostCount(0)   // TODO: 커뮤니티 기능 생기면 업데이트 필요
                .build();
    }
}
