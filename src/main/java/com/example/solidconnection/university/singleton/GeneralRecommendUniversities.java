package com.example.solidconnection.university.singleton;

import com.example.solidconnection.entity.UniversityInfoForApply;
import com.example.solidconnection.university.repository.UniversityInfoForApplyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.solidconnection.constants.constants.*;

@Component
@RequiredArgsConstructor
public class GeneralRecommendUniversities {
    private final UniversityInfoForApplyRepository universityInfoForApplyRepository;
    private List<UniversityInfoForApply> recommendedUniversities;

    @PostConstruct
    public void init() {
        recommendedUniversities = new ArrayList<>();

        UniversityInfoForApply univ1 = universityInfoForApplyRepository.findByUniversity_KoreanName(RECOMMEND_UNIVERSITY_1).get();
        UniversityInfoForApply univ2 = universityInfoForApplyRepository.findByUniversity_KoreanName(RECOMMEND_UNIVERSITY_2).get();
        UniversityInfoForApply univ3 = universityInfoForApplyRepository.findByUniversity_KoreanName(RECOMMEND_UNIVERSITY_3).get();
        UniversityInfoForApply univ4 = universityInfoForApplyRepository.findByUniversity_KoreanName(RECOMMEND_UNIVERSITY_4).get();
        UniversityInfoForApply univ5 = universityInfoForApplyRepository.findByUniversity_KoreanName(RECOMMEND_UNIVERSITY_5).get();
        UniversityInfoForApply univ6 = universityInfoForApplyRepository.findByUniversity_KoreanName(RECOMMEND_UNIVERSITY_6).get();

        recommendedUniversities.add(univ1);
        recommendedUniversities.add(univ2);
        recommendedUniversities.add(univ3);
        recommendedUniversities.add(univ4);
        recommendedUniversities.add(univ5);
        recommendedUniversities.add(univ6);
    }

    public List<UniversityInfoForApply> getRecommendedUniversities() {
        return recommendedUniversities;
    }
}
