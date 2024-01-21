package com.example.solidconnection.type;

public enum ExchangeSemester {
    ONE_SEMESTER("1개학기"),
    NO_PREFERENCE("무관");

    private final String koreanName;

    ExchangeSemester(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
