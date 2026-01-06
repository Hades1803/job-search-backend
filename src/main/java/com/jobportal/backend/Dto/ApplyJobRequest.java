package com.jobportal.backend.Dto;

import lombok.Data;

@Data
public class ApplyJobRequest {
    private Integer candidateId;
    private Integer jobPostingId;
    private Integer resumeId;
    private String resumeLink;
}