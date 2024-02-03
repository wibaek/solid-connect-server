package com.example.solidconnection.s3;

import com.example.solidconnection.custom.response.CustomResponse;
import com.example.solidconnection.custom.response.DataResponse;
import com.example.solidconnection.type.ImgType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/img")
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/profile/pre")
    public CustomResponse uploadPreProfileImage(@RequestParam("imageFile") MultipartFile imageFile) {
        ImageUrlDto profileImageUrl = s3Service.uploadImgFile(imageFile, ImgType.PROFILE);
        return new DataResponse<>(profileImageUrl);
    }

    @PostMapping("/profile/post")
    public CustomResponse uploadPostProfileImage(@RequestParam("imageFile") MultipartFile imageFile, Principal principal) {
        ImageUrlDto profileImageUrl = s3Service.uploadImgFile(imageFile, ImgType.PROFILE);
        s3Service.deleteExProfile(principal.getName());
        return new DataResponse<>(profileImageUrl);
    }

    @PostMapping("/gpa")
    public CustomResponse uploadGpaImage(@RequestParam("imageFile") MultipartFile imageFile) {
        ImageUrlDto profileImageUrl = s3Service.uploadImgFile(imageFile, ImgType.GPA);
        return new DataResponse<>(profileImageUrl);
    }

    @PostMapping("/language-test")
    public CustomResponse uploadLanguageImage(@RequestParam("imageFile") MultipartFile imageFile) {
        ImageUrlDto profileImageUrl = s3Service.uploadImgFile(imageFile, ImgType.LANGUAGE_TEST);
        return new DataResponse<>(profileImageUrl);
    }
}
