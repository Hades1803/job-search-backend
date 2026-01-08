package com.jobportal.backend.Dto;


import lombok.Data;

@Data
public class ResetPasswordRequest {
    private int otp;
    private String newPassword;
}
