package com.jobportal.backend.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResumeResponse {
    private Integer id;
    private String resumeName;
    private String templateCode;
    private String content;
    private LocalDateTime updatedAt;
}
