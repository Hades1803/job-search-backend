package com.jobportal.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingResponse {
    private Integer id;
    private String employerName;
    private String employerLogo;
    private String salaryLevelRange;
    private String addressName;
    private String jobTitle;
    private String workAddress;
    private Integer quantity;
    private String genderRequirement;
    private String jobDescription;
    private String candidateRequirement;
    private String relatedSkills;
    private String benefits;
    private LocalDateTime postedDate;
    private LocalDateTime expirationDate;
    private Integer views;
    private String note;
    private Boolean status;


    private String majorId;
    private String majorName;

    private String jobTypeId;
    private String jobTypeName;

    private String salaryLevelId;
    private String salaryLevelName;

    private String rankId;
    private String rankName;

    private String addressId;
}