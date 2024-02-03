package com.example.solidconnection.type;

public enum SemesterAvailableForDispatch {
    ONE_SEMESTER("1개학기"),
    ONE_YEAR("1년만 가능"),
    IRRELEVANT("무관");


    private final String koreanName;

    SemesterAvailableForDispatch(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
