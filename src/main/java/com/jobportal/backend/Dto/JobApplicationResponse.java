package com.jobportal.backend.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobApplicationResponse {

    private Integer applicationId;

    private Integer candidateId;
    private String candidateName;
    private String candidateEmail;

    private String resumeType;
    private String status;
    private LocalDateTime applyDate;
}