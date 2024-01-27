package com.example.solidconnection.entity;

import com.example.solidconnection.type.RegionCode;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Region {
    @Id
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private RegionCode regionCode;

    // 연관 관계
    @OneToMany(mappedBy = "region")
    private Set<Country> countries;

    @OneToMany(mappedBy = "region")
    private Set<InterestedRegion> interestedRegions;

    @OneToMany(mappedBy = "region")
    private Set<University> universities;
}
