package com.jobportal.backend.Dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CandidateProfileRequest {
    private String name;
    private String phone;
    private String gender;
    private LocalDate birthDate;
    private String avatar;
    private String coverImage;
    private String address;
}