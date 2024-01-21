package com.example.solidconnection.type;

public enum CountryCode {
    BRUNEI("브루나이"),
    SINGAPORE("싱가포르"),
    AZERBAIJAN("아제르바이잔"),
    INDONESIA("인도네시아"),
    JAPAN("일본"),
    TURKEY("튀르키예"),
    HONG_KONG("홍콩"),
    UNITED_STATES("미국"),
    CANADA("캐나다"),
    AUSTRALIA("호주"),
    BRAZIL("브라질"),
    NETHERLANDS("네덜란드"),
    NORWAY("노르웨이"),
    DENMARK("덴마크"),
    GERMANY("독일"),
    SWEDEN("스웨덴"),
    SWITZERLAND("스위스"),
    SPAIN("스페인"),
    UNITED_KINGDOM("영국"),
    AUSTRIA("오스트리아"),
    ITALY("이탈리아"),
    CZECH_REPUBLIC("체코"),
    PORTUGAL("포르투갈"),
    FRANCE("프랑스"),
    FINLAND("핀란드"),
    CHINA("중국"),
    TAIWAN("대만");

    private final String koreanName;

    CountryCode(String koreanName) {
        this.koreanName = koreanName;
    }

    public static CountryCode getCountryCodeByKoreanName(String koreanName) {
        for (CountryCode countryCode : CountryCode.values()) {
            if (countryCode.getKoreanName().equals(koreanName)) {
                return countryCode;
            }
        }
        throw new IllegalArgumentException("No country found with Korean name: " + koreanName); //TODO: 에러 타입 정리 필요
    }

    public String getKoreanName() {
        return koreanName;
    }
}
