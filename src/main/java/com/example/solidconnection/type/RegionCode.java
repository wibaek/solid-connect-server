package com.example.solidconnection.type;

import com.example.solidconnection.custom.exception.CustomException;

import java.util.Arrays;
import java.util.Optional;

import static com.example.solidconnection.custom.exception.ErrorCode.INVALID_REGION_NAME;

public enum RegionCode {
    ASIA("아시아권"),
    AMERICAS("미주권"),
    CHINA("중국권"),
    EUROPE("유럽권");

    private final String koreanName;

    RegionCode(String koreanName) {
        this.koreanName = koreanName;
    }

    public static RegionCode getRegionCodeByKoreanName(String koreanName) {
        Optional<RegionCode> matchingRegionCode = Arrays.stream(RegionCode.values())
                .filter(regionCode -> regionCode.getKoreanName().equals(koreanName))
                .findFirst();
        return matchingRegionCode.orElseThrow(() -> new CustomException(INVALID_REGION_NAME, koreanName));
    }

    public String getKoreanName() {
        return koreanName;
    }
}
