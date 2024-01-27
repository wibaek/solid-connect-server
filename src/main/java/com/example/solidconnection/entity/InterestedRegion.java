package com.example.solidconnection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InterestedRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "site_user_id")
    private SiteUser siteUser;

    @ManyToOne
    @JoinColumn(name = "region_code")
    private Region region;
}
