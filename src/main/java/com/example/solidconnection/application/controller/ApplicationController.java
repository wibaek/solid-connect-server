package com.example.solidconnection.application.controller;

import com.example.solidconnection.application.dto.ApplicationsDto;
import com.example.solidconnection.application.dto.ScoreRequestDto;
import com.example.solidconnection.application.dto.UniversityRequestDto;
import com.example.solidconnection.application.service.ApplicationService;
import com.example.solidconnection.custom.response.CustomResponse;
import com.example.solidconnection.custom.response.DataResponse;
import com.example.solidconnection.custom.response.StatusResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public CustomResponse getApplicants(
            Principal principal,
            @RequestParam(required = false, defaultValue = "") String region,
            @RequestParam(required = false, defaultValue = "") String keyword) {
        ApplicationsDto result = applicationService.getApplicants(principal.getName(), region, keyword);
        return new DataResponse<>(result);
    }
}