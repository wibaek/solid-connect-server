package com.example.solidconnection.application.service;

import com.example.solidconnection.application.repository.ApplicationRepository;
import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.entity.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.solidconnection.custom.exception.ErrorCode.APPLICATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ApplicationValidator {
    private final ApplicationRepository applicationRepository;

    public Application getValidatedApplicationBySiteUser_Email(String email){
        return applicationRepository.findBySiteUser_Email(email)
                .orElseThrow(() -> new CustomException(APPLICATION_NOT_FOUND));
    }
}
