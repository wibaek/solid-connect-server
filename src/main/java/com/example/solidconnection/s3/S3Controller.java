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
@RequestMapping("/file")
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/profile/pre")
    public CustomResponse uploadPreProfileImage(@RequestParam("file") MultipartFile imageFile) {
        UploadedFileURLDto profileImageUrl = s3Service.uploadFile(imageFile, ImgType.PROFILE);
        return new DataResponse<>(profileImageUrl);
    }

    @PostMapping("/profile/post")
    public CustomResponse uploadPostProfileImage(@RequestParam("file") MultipartFile imageFile, Principal principal) {
        UploadedFileURLDto profileImageUrl = s3Service.uploadFile(imageFile, ImgType.PROFILE);
        s3Service.deleteExProfile(principal.getName());
        return new DataResponse<>(profileImageUrl);
    }

    @PostMapping("/gpa")
    public CustomResponse uploadGpaImage(@RequestParam("file") MultipartFile imageFile) {
        UploadedFileURLDto profileImageUrl = s3Service.uploadFile(imageFile, ImgType.GPA);
        return new DataResponse<>(profileImageUrl);
    }

    @PostMapping("/language-test")
    public CustomResponse uploadLanguageImage(@RequestParam("file") MultipartFile imageFile) {
        UploadedFileURLDto profileImageUrl = s3Service.uploadFile(imageFile, ImgType.LANGUAGE_TEST);
        return new DataResponse<>(profileImageUrl);
    }
}
