package com.example.solidconnection.constants;

import com.example.solidconnection.entity.UniversityInfoForApply;
import com.example.solidconnection.university.repository.UniversityInfoForApplyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.solidconnection.constants.Constants.TERM;

@Component
@RequiredArgsConstructor
public class GeneralRecommendUniversities {
    // 기본 추천 대학 - 국문명
    public final static String RECOMMEND_UNIVERSITY_1 = "네바다주립대학 라스베이거스(B형)";
    public final static String RECOMMEND_UNIVERSITY_2 = "바덴뷔르템베르크 산학협력대학";
    public final static String RECOMMEND_UNIVERSITY_3 = "릴 가톨릭 대학";
    public final static String RECOMMEND_UNIVERSITY_4 = "그라츠공과대학";
    public final static String RECOMMEND_UNIVERSITY_5 = "RMIT멜버른공과대학(A형)";
    public final static String RECOMMEND_UNIVERSITY_6 = "오스트라바 대학";

    private final UniversityInfoForApplyRepository universityInfoForApplyRepository;
    private List<UniversityInfoForApply> recommendedUniversities;

    @PostConstruct
    public void init() {
        recommendedUniversities = new ArrayList<>();

        UniversityInfoForApply univ1 = universityInfoForApplyRepository.findByUniversity_KoreanNameAndTerm(RECOMMEND_UNIVERSITY_1, TERM).get();
        UniversityInfoForApply univ2 = universityInfoForApplyRepository.findByUniversity_KoreanNameAndTerm(RECOMMEND_UNIVERSITY_2, TERM).get();
        UniversityInfoForApply univ3 = universityInfoForApplyRepository.findByUniversity_KoreanNameAndTerm(RECOMMEND_UNIVERSITY_3, TERM).get();
        UniversityInfoForApply univ4 = universityInfoForApplyRepository.findByUniversity_KoreanNameAndTerm(RECOMMEND_UNIVERSITY_4, TERM).get();
        UniversityInfoForApply univ5 = universityInfoForApplyRepository.findByUniversity_KoreanNameAndTerm(RECOMMEND_UNIVERSITY_5, TERM).get();
        UniversityInfoForApply univ6 = universityInfoForApplyRepository.findByUniversity_KoreanNameAndTerm(RECOMMEND_UNIVERSITY_6, TERM).get();

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
