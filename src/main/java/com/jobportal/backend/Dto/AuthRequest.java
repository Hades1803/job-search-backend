package com.jobportal.backend.Dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
