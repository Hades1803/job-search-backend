package com.jobportal.backend.Dto;


import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private Integer otp;
    private String newPassword;
}
