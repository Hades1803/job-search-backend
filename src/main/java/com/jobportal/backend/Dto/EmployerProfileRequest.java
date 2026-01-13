package com.jobportal.backend.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmployerProfileRequest {

    private String employerName;
    private String representativeName;
    private String representativePosition;
    private String phone;
    private String scale;
    private String description;
    private String address;
    private String website;

    // FILE UPLOAD
    private MultipartFile logoFile;
    private MultipartFile coverFile;
}