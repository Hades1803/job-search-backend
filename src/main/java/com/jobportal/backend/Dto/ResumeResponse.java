package com.jobportal.backend.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResumeResponse {

    private String resumeType;   // DB_RESUME | UPLOADED_FILE | LINK_ONLY

    // ===== DB_RESUME =====
    private Integer id;
    private String resumeName;
    private String templateCode;
    private String content;
    private LocalDateTime updatedAt;

    // ===== UPLOADED_FILE =====
    private String cvPreviewUrl;

    // ===== LINK_ONLY =====
    private String resumeLink;
}

