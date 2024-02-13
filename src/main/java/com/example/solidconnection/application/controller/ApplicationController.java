package com.example.solidconnection.application.controller;

import com.example.solidconnection.application.dto.ScoreRequestDto;
import com.example.solidconnection.application.dto.UniversityRequestDto;
import com.example.solidconnection.application.service.ApplicationService;
import com.example.solidconnection.custom.response.CustomResponse;
import com.example.solidconnection.custom.response.StatusResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/score")
    public CustomResponse registerScore(Principal principal, @Valid @RequestBody ScoreRequestDto scoreRequestDto) {
        boolean result = applicationService.saveScore(principal.getName(), scoreRequestDto);
        return new StatusResponse(result);
    }

    @PostMapping("/university")
    public CustomResponse registerUniversity(Principal principal, @Valid @RequestBody UniversityRequestDto universityRequestDto) {
        boolean result = applicationService.saveUniversity(principal.getName(), universityRequestDto);
        return new StatusResponse(result);
    }
}