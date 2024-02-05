package com.example.solidconnection.entity;

import com.example.solidconnection.type.RegionCode;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Region {
    @Id
    @Column(length = 10, name = "region_code", columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private RegionCode code;
}
