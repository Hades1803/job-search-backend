package com.jobportal.backend.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class CandidateProfileRequest {
    private String name;
    private String phone;
    private String gender;
    private LocalDate birthDate;
    private MultipartFile avatarFile;
    private MultipartFile coverImageFile;
    private String coverImage;
    private String address;
}