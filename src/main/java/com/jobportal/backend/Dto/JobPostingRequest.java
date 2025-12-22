package com.jobportal.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingRequest {
    private String jobTitle;
    private String workAddress;
    private Integer quantity;
    private String genderRequirement;
    private String jobDescription;
    private String candidateRequirement;
    private String relatedSkills;
    private String benefits;
    private LocalDateTime expirationDate;
    private String note;
    private Boolean status;
    // Đổi từ Integer thành String (vì ID là String)
    private String majorId;
    private String jobTypeId;
    private String salaryLevelId;
    private String rankId;
    private String addressId;
}