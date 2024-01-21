package com.example.solidconnection.entity;

import com.example.solidconnection.type.CountryCode;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Country {
    @Id
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    @Column(nullable = false, length = 100)
    private String name;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "country")
    private Set<University> universities;

    @OneToMany(mappedBy = "country")
    private Set<InterestedCountry> interestedCountries;
}
