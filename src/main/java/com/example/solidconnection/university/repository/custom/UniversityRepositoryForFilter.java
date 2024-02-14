package com.example.solidconnection.university.repository.custom;

import com.example.solidconnection.entity.University;
import com.example.solidconnection.type.CountryCode;
import com.example.solidconnection.type.RegionCode;

import java.util.List;

public interface UniversityRepositoryForFilter {
    List<University> findByRegionAndKeyword(RegionCode regionCode, List<CountryCode> countryCodes, String keyword);
}
