package com.example.solidconnection.home.controller;

import com.example.solidconnection.custom.response.CustomResponse;
import com.example.solidconnection.custom.response.DataResponse;
import com.example.solidconnection.home.dto.PersonalHomeInfoDto;
import com.example.solidconnection.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final UniversityService universityService;

    @GetMapping
    public CustomResponse getHomeInfo(Principal principal) {
        PersonalHomeInfoDto personalHomeInfoDto = new PersonalHomeInfoDto();
        if (principal == null) {
            personalHomeInfoDto.setRecommendedUniversities(universityService.getGeneralRecommends());
        } else {
            personalHomeInfoDto.setRecommendedUniversities(universityService.getPersonalRecommends(principal.getName()));
        }
        return new DataResponse<>(personalHomeInfoDto);
    }
}