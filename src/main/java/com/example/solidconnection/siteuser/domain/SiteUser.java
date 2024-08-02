package com.example.solidconnection.siteuser.domain;

import com.example.solidconnection.entity.Comment;
import com.example.solidconnection.entity.Post;
import com.example.solidconnection.entity.mapping.PostLike;
import com.example.solidconnection.type.Gender;
import com.example.solidconnection.type.PreparationStatus;
import com.example.solidconnection.type.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Setter
    @Column(nullable = false, length = 100)
    private String nickname;

    @Setter
    @Column(length = 500)
    private String profileImageUrl;

    @Column(nullable = false, length = 20)
    private String birth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PreparationStatus preparationStage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Setter
    private LocalDateTime nicknameModifiedAt;

    @Setter
    private LocalDate quitedAt;

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private List<PostLike> postLikeList = new ArrayList<>();

    public SiteUser(
            String email,
            String nickname,
            String profileImageUrl,
            String birth,
            PreparationStatus preparationStage,
            Role role,
            Gender gender) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.birth = birth;
        this.preparationStage = preparationStage;
        this.role = role;
        this.gender = gender;
    }
}
