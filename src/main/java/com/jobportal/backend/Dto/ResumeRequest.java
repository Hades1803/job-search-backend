package com.jobportal.backend.Dto;


import lombok.Data;

@Data
public class ResumeRequest {
    private String resumeName;
    private String majorId;
    private String jobTypeId;
    private String rankId;

    private String careerObjective;
    private String experience;
    private String skills;
    private String education;
    private String softSkills;
    private String awards;
}
