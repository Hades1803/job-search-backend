package com.jobportal.backend.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ApplyJobRequest {
    private Integer jobPostingId;

    private Integer resumeId;
    private MultipartFile cvFile;
    private String resumeLink;
}
