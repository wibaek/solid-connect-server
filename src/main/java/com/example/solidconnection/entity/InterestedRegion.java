package com.example.solidconnection.entity;

import jakarta.persistence.*;

@Entity
public class InterestedRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "site_user_id")
    private SiteUser siteUser;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}
