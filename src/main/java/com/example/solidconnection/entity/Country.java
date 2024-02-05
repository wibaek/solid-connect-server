package com.example.solidconnection.entity;

import com.example.solidconnection.type.CountryCode;
import jakarta.persistence.*;

@Entity
public class Country {
    @Id
    @Column(length = 2, name = "country_code", columnDefinition = "VARCHAR(2)")
    @Enumerated(EnumType.STRING)
    private CountryCode code;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "region_code", referencedColumnName="region_code")
    private Region region;
}
