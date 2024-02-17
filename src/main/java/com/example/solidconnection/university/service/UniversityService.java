package com.example.solidconnection.university.service;

import com.example.solidconnection.constants.GeneralRecommendUniversities;
import com.example.solidconnection.entity.*;
import com.example.solidconnection.home.dto.RecommendedUniversityDto;
import com.example.solidconnection.repositories.InterestedCountyRepository;
import com.example.solidconnection.repositories.InterestedRegionRepository;
import com.example.solidconnection.siteuser.repository.LikedUniversityRepository;
import com.example.solidconnection.siteuser.service.SiteUserValidator;
import com.example.solidconnection.type.CountryCode;
import com.example.solidconnection.type.LanguageTestType;
import com.example.solidconnection.type.RegionCode;
import com.example.solidconnection.university.dto.LanguageRequirementDto;
import com.example.solidconnection.university.dto.LikedResultDto;
import com.example.solidconnection.university.dto.UniversityDetailDto;
import com.example.solidconnection.university.dto.UniversityPreviewDto;
import com.example.solidconnection.university.repository.LanguageRequirementRepository;
import com.example.solidconnection.university.repository.UniversityInfoForApplyRepository;
import com.example.solidconnection.university.repository.UniversityRepository;
import com.example.solidconnection.university.repository.custom.UniversityRepositoryForFilterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.solidconnection.constants.Constants.RECOMMEND_UNIVERSITY_NUM;
import static com.example.solidconnection.constants.Constants.TERM;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityInfoForApplyRepository universityInfoForApplyRepository;
    private final UniversityRepository universityRepository;
    private final LanguageRequirementRepository languageRequirementRepository;
    private final InterestedCountyRepository interestedCountyRepository;
    private final InterestedRegionRepository interestedRegionRepository;
    private final LikedUniversityRepository likedUniversityRepository;
    private final SiteUserValidator siteUserValidator;
    private final GeneralRecommendUniversities generalRecommendUniversities;
    private final UniversityValidator universityValidator;
    private final UniversityRepositoryForFilterImpl universityRepositoryForFilter;

    public List<RecommendedUniversityDto> getPersonalRecommends(String email) {
        SiteUser siteUser = siteUserValidator.getValidatedSiteUserByEmail(email);
        List<CountryCode> interestedCountries = interestedCountyRepository.findAllBySiteUser(siteUser)
                .stream().map(interestedCountry -> interestedCountry.getCountry().getCode())
                .toList();
        List<RegionCode> interestedRegions = interestedRegionRepository.findAllBySiteUser(siteUser)
                .stream().map(interestedRegion -> interestedRegion.getRegion().getCode())
                .toList();
        List<UniversityInfoForApply> recommendedUniversities = new java.util.ArrayList<>(universityRepository.findByCountryCodeInOrRegionCodeIn(interestedCountries, interestedRegions)
                .stream().map(universityValidator::getValidatedUniversityInfoForApplyByUniversity)
                .toList());

        Collections.shuffle(recommendedUniversities);
        List<UniversityInfoForApply> shuffledList = recommendedUniversities.subList(0, Math.min(RECOMMEND_UNIVERSITY_NUM, recommendedUniversities.size()));
        if (shuffledList.size() < 6) {
            shuffledList.addAll(getGeneralRecommendsExcept(shuffledList));
        }

        return shuffledList.stream().map(RecommendedUniversityDto::fromEntity).collect(Collectors.toList());
    }

    public List<RecommendedUniversityDto> getGeneralRecommends() {
        List<UniversityInfoForApply> generalRecommend = new java.util.ArrayList<>(generalRecommendUniversities.getRecommendedUniversities());
        Collections.shuffle(generalRecommend);
        return generalRecommend.stream().map(RecommendedUniversityDto::fromEntity).collect(Collectors.toList());
    }

    private List<UniversityInfoForApply> getGeneralRecommendsExcept(List<UniversityInfoForApply> alreadyPicked) {
        List<UniversityInfoForApply> generalRecommend = new java.util.ArrayList<>(generalRecommendUniversities.getRecommendedUniversities());
        generalRecommend.removeAll(alreadyPicked);
        int sizeToPick = RECOMMEND_UNIVERSITY_NUM - alreadyPicked.size();
        Collections.shuffle(generalRecommend);
        return generalRecommend.subList(0, sizeToPick);
    }

    public UniversityDetailDto getDetail(Long universityInfoForApplyId) {
        UniversityInfoForApply universityInfoForApply = universityValidator.getValidatedUniversityInfoForApplyById(universityInfoForApplyId);
        University university = universityValidator.getValidatedUniversityById(universityInfoForApply.getUniversity().getId());

        List<LanguageRequirementDto> languageRequirements = languageRequirementRepository
                .findAllByUniversityInfoForApply_Id(universityInfoForApplyId)
                .stream()
                .map(LanguageRequirementDto::fromEntity)
                .toList();

        String countryKoreanName = null;
        String regionKoreanName = null;
        if (university.getCountry() != null) {
            countryKoreanName = university.getCountry().getCode().getKoreanName();
        }
        if (university.getCountry() != null) {
            regionKoreanName = university.getRegion().getCode().getKoreanName();
        }

        return UniversityDetailDto.builder()
                .id(university.getId())
                .term(universityInfoForApply.getTerm())
                .koreanName(university.getKoreanName())
                .englishName(university.getEnglishName())
                .formatName(university.getFormatName())
                .region(regionKoreanName)
                .country(countryKoreanName)
                .homepageUrl(university.getHomepageUrl())
                .logoImageUrl(university.getLogoImageUrl())
                .backgroundImageUrl(university.getBackgroundImageUrl())
                .detailsForLocal(university.getDetailsForLocal())
                .studentCapacity(universityInfoForApply.getStudentCapacity())
                .tuitionFeeType(universityInfoForApply.getTuitionFeeType().getKoreanName())
                .semesterAvailableForDispatch(universityInfoForApply.getSemesterAvailableForDispatch().getKoreanName())
                .languageRequirements(languageRequirements)
                .detailsForLanguage(universityInfoForApply.getDetailsForLanguage())
                .gpaRequirement(universityInfoForApply.getGpaRequirement())
                .gpaRequirementCriteria(universityInfoForApply.getGpaRequirementCriteria())
                .semesterRequirement(universityInfoForApply.getSemesterRequirement())
                .detailsForApply(universityInfoForApply.getDetailsForApply())
                .detailsForMajor(universityInfoForApply.getDetailsForMajor())
                .detailsForAccommodation(universityInfoForApply.getDetailsForAccommodation())
                .detailsForEnglishCourse(universityInfoForApply.getDetailsForEnglishCourse())
                .details(universityInfoForApply.getDetails())
                .accommodationUrl(university.getAccommodationUrl())
                .englishCourseUrl(university.getEnglishCourseUrl())
                .build();
    }


    public List<UniversityPreviewDto> search(String region, List<String> keywords, LanguageTestType testType, String testScore) {
        RegionCode regionCode = null;
        if (region != null && !region.isBlank()) {
            regionCode = RegionCode.getRegionCodeByKoreanName(region);
        }

        List<CountryCode> countryCodes = null;
        if (keywords != null && !keywords.isEmpty()) {
            countryCodes = CountryCode.getCountryCodeMatchesToKeyword(keywords);
        }

        List<University> universities = universityRepositoryForFilter.findByRegionAndCountryAndKeyword(regionCode, countryCodes, keywords);
        return universities.stream()
                .filter(university -> universityInfoForApplyRepository.existsByUniversityAndTerm(university, TERM))
                .filter(university -> {
                    UniversityInfoForApply universityInfoForApply = universityValidator.getValidatedUniversityInfoForApplyByUniversity(university);
                    return languageRequirementRepository.findByUniversityInfoForApplyAndLanguageTestTypeAndLessThanMyScore(universityInfoForApply, testType, testScore).isPresent();
                })
                .map(university -> {
                    UniversityInfoForApply universityInfoForApply = universityValidator.getValidatedUniversityInfoForApplyByUniversity(university);
                    return UniversityPreviewDto.fromEntity(universityInfoForApply);
                })
                .toList();
    }

    public LikedResultDto like(String email, Long universityInfoForApplyId) {
        SiteUser siteUser = siteUserValidator.getValidatedSiteUserByEmail(email);
        UniversityInfoForApply universityInfoForApply = universityValidator.getValidatedUniversityInfoForApplyById(universityInfoForApplyId);

        Optional<LikedUniversity> alreadyLikedUniversity = likedUniversityRepository.findBySiteUserAndUniversityInfoForApply(siteUser, universityInfoForApply);
        if (alreadyLikedUniversity.isPresent()) {
            likedUniversityRepository.delete(alreadyLikedUniversity.get());
            return LikedResultDto.builder()
                    .result("LIKE_CANCELED")
                    .build();
        }

        LikedUniversity likedUniversity = LikedUniversity.builder()
                .universityInfoForApply(universityInfoForApply)
                .siteUser(siteUser)
                .build();
        likedUniversityRepository.save(likedUniversity);
        return LikedResultDto.builder()
                .result("LIKE_SUCCESS")
                .build();
    }

    public boolean getIsLiked(String email, Long universityInfoForApplyId) {
        SiteUser siteUser = siteUserValidator.getValidatedSiteUserByEmail(email);
        UniversityInfoForApply universityInfoForApply = universityValidator.getValidatedUniversityInfoForApplyById(universityInfoForApplyId);
        return likedUniversityRepository.findBySiteUserAndUniversityInfoForApply(siteUser, universityInfoForApply)
                .isPresent();
    }
}
