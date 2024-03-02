package com.example.solidconnection.university.service;

import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.entity.University;
import com.example.solidconnection.entity.UniversityInfoForApply;
import com.example.solidconnection.university.repository.LanguageRequirementRepository;
import com.example.solidconnection.university.repository.UniversityInfoForApplyRepository;
import com.example.solidconnection.university.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.solidconnection.constants.Constants.TERM;
import static com.example.solidconnection.custom.exception.ErrorCode.UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND;
import static com.example.solidconnection.custom.exception.ErrorCode.UNIVERSITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UniversityValidator {
    private final UniversityInfoForApplyRepository universityInfoForApplyRepository;
    private final UniversityRepository universityRepository;
    private final LanguageRequirementRepository languageRequirementRepository;

    public UniversityInfoForApply getValidatedUniversityInfoForApplyByIdAndTerm(Long id) {
        return universityInfoForApplyRepository.findByIdAndTerm(id, TERM)
                .orElseThrow(() -> new CustomException(UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND));
    }

    public UniversityInfoForApply getValidatedUniversityInfoForApplyById(Long id) {
        return universityInfoForApplyRepository.findById(id)
                .orElseThrow(() -> new CustomException(UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND));
    }

    public UniversityInfoForApply getValidatedUniversityInfoForApplyByUniversityAndTerm(University university) {
        return universityInfoForApplyRepository.findByUniversityAndTerm(university, TERM)
                .orElseThrow(() -> new CustomException(UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND));
    }

    public UniversityInfoForApply getValidatedUniversityInfoForApplyByUniversityAndTermNoException(University university) {
        return universityInfoForApplyRepository.findByUniversityAndTerm(university, TERM)
                .orElse(null);
    }

    public University getValidatedUniversityById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new CustomException(UNIVERSITY_NOT_FOUND));
    }
}
