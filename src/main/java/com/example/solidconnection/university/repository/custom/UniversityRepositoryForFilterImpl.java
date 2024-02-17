package com.example.solidconnection.university.repository.custom;

import com.example.solidconnection.entity.QCountry;
import com.example.solidconnection.entity.QRegion;
import com.example.solidconnection.entity.QUniversity;
import com.example.solidconnection.entity.University;
import com.example.solidconnection.type.CountryCode;
import com.example.solidconnection.type.RegionCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UniversityRepositoryForFilterImpl implements UniversityRepositoryForFilter {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public UniversityRepositoryForFilterImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<University> findByRegionAndCountryAndKeyword(RegionCode regionCode, List<CountryCode> countryCodes, List<String> keywords) {
        QUniversity university = QUniversity.university;
        QCountry country = QCountry.country;
        QRegion region = QRegion.region;

        // System.out.println(regionCodeEq(regionCode, region).and(keywordContainsInCountryOrName(countryCodes, country, keywords, university)));

        return queryFactory
                .selectFrom(university)
                .join(university.country, country)
                .join(country.region, region)
                .where(
                        regionCodeEq(regionCode, region).and(keywordContainsInCountryOrName(countryCodes, country, keywords, university))
                )
                .fetch();
    }

    private BooleanExpression regionCodeEq(RegionCode regionCode, QRegion region) {
        if(regionCode == null) {
            return Expressions.asBoolean(true).isTrue();
        }
        return region.code.eq(regionCode);
    }

    private BooleanExpression keywordContainsInCountryOrName(List<CountryCode> countryCodes, QCountry country, List<String> keywords, QUniversity university) {
        if (countryCodes == null || countryCodes.isEmpty()) { // 해당하는 국가가 없으면
            if (keywords == null || keywords.isEmpty()) {
                return Expressions.asBoolean(true).isTrue();
            }
            return keywords.stream()
                    .map(university.koreanName::contains)
                    .reduce(BooleanExpression::or)
                    .orElse(Expressions.asBoolean(true).isFalse());
        }

        BooleanExpression countryCondition = country.code.in(countryCodes);
        if (keywords == null || keywords.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }

        BooleanExpression keywordCondition = keywords.stream()
                .map(university.koreanName::contains)
                .reduce(BooleanExpression::or)
                .orElse(Expressions.asBoolean(true).isFalse());
        return countryCondition.or(keywordCondition);
    }
}
