package com.jobportal.backend.Dto;



import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Integer roleId;
}
