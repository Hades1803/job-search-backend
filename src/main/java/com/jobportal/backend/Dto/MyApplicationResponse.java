package com.jobportal.backend.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyApplicationResponse {

    private Integer applicationId;

    private Integer jobId;
    private String jobTitle;
    private String companyName;

    private String resumeType;
    private String status;

    private LocalDateTime applyDate;
}