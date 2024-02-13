package com.example.solidconnection.application.service;

import com.example.solidconnection.application.dto.ScoreRequestDto;
import com.example.solidconnection.application.dto.UniversityRequestDto;
import com.example.solidconnection.application.repository.ApplicationRepository;
import com.example.solidconnection.constants.NicknameForApplyWords;
import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.entity.Application;
import com.example.solidconnection.entity.SiteUser;
import com.example.solidconnection.entity.UniversityInfoForApply;
import com.example.solidconnection.siteuser.service.SiteUserValidator;
import com.example.solidconnection.university.service.UniversityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;

import static com.example.solidconnection.constants.Constants.APPLICATION_UPDATE_COUNT_LIMIT;
import static com.example.solidconnection.custom.exception.ErrorCode.APPLY_UPDATE_LIMIT_EXCEED;
import static com.example.solidconnection.custom.exception.ErrorCode.CANT_APPLY_FOR_SAME_UNIVERSITY;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UniversityValidator universityValidator;
    private final SiteUserValidator siteUserValidator;
    private final ApplicationValidator applicationValidator;

    public boolean saveScore(String email, ScoreRequestDto scoreRequestDto) {
        // 한번 등록 후 수정
        if (applicationRepository.existsBySiteUser_Email(email)) {
            Application application = applicationValidator.getValidatedApplicationBySiteUser_Email(email);
            // 수정 횟수 초과 에러 처리
            if (application.getUpdateCount() > APPLICATION_UPDATE_COUNT_LIMIT) {
                throw new CustomException(APPLY_UPDATE_LIMIT_EXCEED);
            }
            application.setGpa(scoreRequestDto.getGpa());
            application.setGpaCriteria(scoreRequestDto.getGpaCriteria());
            application.setGpaReportUrl(scoreRequestDto.getGpaReportUrl());
            application.setLanguageTestScore(scoreRequestDto.getLanguageTestScore());
            application.setLanguageTestType(scoreRequestDto.getLanguageTestType());
            application.setLanguageTestReportUrl(scoreRequestDto.getLanguageTestReportUrl());
            application.setUpdateCount(application.getUpdateCount() + 1);
            return true;
        }

        // 최초 증록
        SiteUser siteUser = siteUserValidator.getValidatedSiteUserByEmail(email);
        Application application = Application.saveScore(siteUser, scoreRequestDto);
        applicationRepository.save(application);
        return true;
    }

    public boolean saveUniversity(String email, UniversityRequestDto universityRequestDto) {
        // 수정 횟수 초과 에러 처리
        Application application = applicationValidator.getValidatedApplicationBySiteUser_Email(email);
        if (application.getUpdateCount() > APPLICATION_UPDATE_COUNT_LIMIT) {
            throw new CustomException(APPLY_UPDATE_LIMIT_EXCEED);
        }

        // 저장에 필요한 엔티티 불러오기 or 생성
        UniversityInfoForApply firstChoiceUniversity = universityValidator.getValidatedUniversityInfoForApplyById(universityRequestDto.getFirstChoiceUniversityId());
        UniversityInfoForApply secondChoiceUniversity;
        try {
            secondChoiceUniversity = universityValidator.getValidatedUniversityInfoForApplyById(universityRequestDto.getSecondChoiceUniversity());
        } catch (Exception e) {
            secondChoiceUniversity = null;
        }

        // 1,2 동일한 대학교 지망 에러 처리
        if (secondChoiceUniversity != null && Objects.equals(secondChoiceUniversity.getId(), firstChoiceUniversity.getId())) {
            throw new CustomException(CANT_APPLY_FOR_SAME_UNIVERSITY);
        }

        // 수정
        application.setFirstChoiceUniversity(firstChoiceUniversity);
        application.setFirstChoiceUniversity(secondChoiceUniversity);

        // 새로운 닉네임 부여
        String randomNickname = makeRandomNickname();
        while (applicationRepository.existsByNicknameForApply(randomNickname)) {
            randomNickname = makeRandomNickname();
        }
        application.setNicknameForApply(randomNickname);
        return true;
    }

    private String makeRandomNickname() {
        Random random = new Random();
        int randomIndex1 = random.nextInt(NicknameForApplyWords.adjectives.size());
        String randomAdjective = NicknameForApplyWords.adjectives.get(randomIndex1);
        int randomIndex2 = random.nextInt(NicknameForApplyWords.nouns.size());
        String randomNoun = NicknameForApplyWords.nouns.get(randomIndex2);
        return randomAdjective + " " + randomNoun;
    }
}
