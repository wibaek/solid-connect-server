package com.example.solidconnection.type;

import com.example.solidconnection.custom.exception.CustomException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.solidconnection.custom.exception.ErrorCode.INVALID_COUNTRY_NAME;

public enum CountryCode {
    BN("브루나이"),
    SG("싱가포르"),
    AZ("아제르바이잔"),
    ID("인도네시아"),
    JP("일본"),
    TR("튀르키예"),
    HK("홍콩"),
    US("미국"),
    CA("캐나다"),
    AU("호주"),
    BR("브라질"),
    NL("네덜란드"),
    NO("노르웨이"),
    DK("덴마크"),
    DE("독일"),
    SE("스웨덴"),
    CH("스위스"),
    ES("스페인"),
    GB("영국"),
    AT("오스트리아"),
    IT("이탈리아"),
    CZ("체코"),
    PT("포르투갈"),
    FR("프랑스"),
    FI("핀란드"),
    CN("중국"),
    TW("대만");

    private final String koreanName;

    CountryCode(String koreanName) {
        this.koreanName = koreanName;
    }

    public static CountryCode getCountryCodeByKoreanName(String koreanName) {
        Optional<CountryCode> matchingCountryCode = Arrays.stream(CountryCode.values())
                .filter(countryCode -> countryCode.getKoreanName().equals(koreanName))
                .findFirst();
        return matchingCountryCode.orElseThrow(() -> new CustomException(INVALID_COUNTRY_NAME, koreanName));
    }

    public static List<CountryCode> getCountryCodeMatchesToKeyword(String keyword) {
        return Arrays.stream(CountryCode.values())
                .filter(country -> country.koreanName.contains(keyword))
                .toList();
    }

    public String getKoreanName() {
        return koreanName;
    }
}
