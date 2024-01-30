package com.example.solidconnection.siteuser.service;

import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.entity.SiteUser;
import com.example.solidconnection.siteuser.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.solidconnection.custom.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SiteUserValidator {
    private final SiteUserRepository siteUserRepository;

    public SiteUser validateExistByEmail(String email){
        return siteUserRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
