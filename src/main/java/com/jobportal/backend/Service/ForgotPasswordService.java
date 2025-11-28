package com.jobportal.backend.Service;



import com.jobportal.backend.Dto.ForgotPasswordRequest;
import com.jobportal.backend.Dto.ResetPasswordRequest;

public interface ForgotPasswordService {

    String sendOtp(ForgotPasswordRequest request);

    String resetPassword(ResetPasswordRequest request);
}
