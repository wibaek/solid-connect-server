package com.example.solidconnection.university.controller;

import com.example.solidconnection.custom.response.CustomResponse;
import com.example.solidconnection.custom.response.DataResponse;
import com.example.solidconnection.university.dto.UniversityDetailDto;
import com.example.solidconnection.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("/detail/{universityInfoForApplyId}")
    public CustomResponse getDetails(@PathVariable Long universityInfoForApplyId) {
        UniversityDetailDto universityDetailDto = universityService.getDetail(universityInfoForApplyId);
        return new DataResponse<>(universityDetailDto);
    }
}
