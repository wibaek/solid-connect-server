package com.example.solidconnection.university.service;

import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.entity.SiteUser;
import com.example.solidconnection.entity.University;
import com.example.solidconnection.entity.UniversityInfoForApply;
import com.example.solidconnection.home.dto.RecommendedUniversityDto;
import com.example.solidconnection.repositories.InterestedCountyRepository;
import com.example.solidconnection.repositories.InterestedRegionRepository;
import com.example.solidconnection.siteuser.service.SiteUserValidator;
import com.example.solidconnection.type.CountryCode;
import com.example.solidconnection.type.RegionCode;
import com.example.solidconnection.university.dto.LanguageRequirementDto;
import com.example.solidconnection.university.dto.UniversityDetailDto;
import com.example.solidconnection.university.repository.LanguageRequirementRepository;
import com.example.solidconnection.university.repository.UniversityInfoForApplyRepository;
import com.example.solidconnection.university.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.solidconnection.custom.exception.ErrorCode.UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND;
import static com.example.solidconnection.custom.exception.ErrorCode.UNIVERSITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final static int RECOMMEND_NUM = 6;

    private final UniversityInfoForApplyRepository universityInfoForApplyRepository;
    private final UniversityRepository universityRepository;
    private final LanguageRequirementRepository languageRequirementRepository;
    private final SiteUserValidator siteUserValidator;
    private final InterestedCountyRepository interestedCountyRepository;
    private final InterestedRegionRepository interestedRegionRepository;

    public List<RecommendedUniversityDto> getPersonalRecommends(String email){
        SiteUser siteUser = siteUserValidator.getValidatedSiteUserByEmail(email);
        List<CountryCode> interestedCountries = interestedCountyRepository.findAllBySiteUser(siteUser)
                .stream().map(interestedCountry -> interestedCountry.getCountry().getCode())
                .toList();
        List<RegionCode> interestedRegions = interestedRegionRepository.findAllBySiteUser(siteUser)
                .stream().map(interestedRegion -> interestedRegion.getRegion().getCode())
                .toList();
        List<UniversityInfoForApply> recommendedUniversities = new java.util.ArrayList<>(universityRepository.findByCountryCodeInOrRegionCodeIn(interestedCountries, interestedRegions)
                .stream().map(university -> {
                    return universityInfoForApplyRepository.findByUniversity(university).get();
                })
                .toList());

        Collections.shuffle(recommendedUniversities);
        List<UniversityInfoForApply> shuffledList = recommendedUniversities.subList(0, Math.min(RECOMMEND_NUM, recommendedUniversities.size()));
        if(shuffledList.size() < 6){
            shuffledList.addAll(getRandomRecommendsExcept(shuffledList));
        }

        return shuffledList.stream().map(RecommendedUniversityDto::fromEntity).collect(Collectors.toList());
    }

    public List<RecommendedUniversityDto> getGeneralRecommends(){
        List<UniversityInfoForApply> list = new java.util.ArrayList<>(universityInfoForApplyRepository.findAll());
        Collections.shuffle(list);
        List<UniversityInfoForApply> shuffledList = list.subList(0, RECOMMEND_NUM);
        return shuffledList.stream().map(RecommendedUniversityDto::fromEntity).collect(Collectors.toList());
    }

    private List<UniversityInfoForApply> getRandomRecommendsExcept(List<UniversityInfoForApply> alreadyPicked){
        List<UniversityInfoForApply> list = new java.util.ArrayList<>(universityInfoForApplyRepository.findAll());
        list.removeAll(alreadyPicked);
        int sizeToPick = RECOMMEND_NUM - list.size();
        Collections.shuffle(list);
        return list.subList(0, sizeToPick);
    }

    public UniversityDetailDto getDetail(Long universityInfoForApplyId){
        UniversityInfoForApply universityInfoForApply = getValidatedUniversityInfoForApply(universityInfoForApplyId);
        University university = getValidatedUniversity(universityInfoForApply.getUniversity().getId());

        List<LanguageRequirementDto> languageRequirements = languageRequirementRepository
                .findAllByUniversityInfoForApply_Id(universityInfoForApplyId)
                .stream()
                .map(LanguageRequirementDto::fromEntity)
                .toList();

        String countryKoreanName = null;
        String regionKoreanName = null;
        if(university.getCountry() != null){
            countryKoreanName = university.getCountry().getCode().getKoreanName();
        }
        if(university.getCountry() != null){
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

    private UniversityInfoForApply getValidatedUniversityInfoForApply(Long id){
        return universityInfoForApplyRepository.findById(id)
                .orElseThrow(() -> new CustomException(UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND));
    }

    private University getValidatedUniversity(Long id){
        return universityRepository.findById(id)
                .orElseThrow(() -> new CustomException(UNIVERSITY_NOT_FOUND));
    }
}
