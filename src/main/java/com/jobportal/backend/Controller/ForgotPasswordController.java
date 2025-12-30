package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ForgotPasswordRequest;
import com.jobportal.backend.Dto.ResetPasswordRequest;
import com.jobportal.backend.Service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    // Bước 1: gửi OTP
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return forgotPasswordService.sendOtp(request);
    }

    // Bước 2: verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam int otp) {
        return forgotPasswordService.verifyOtp(otp);
    }

    // Bước 3: reset password (sau khi OTP đã verify)
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam int otp, @RequestBody ResetPasswordRequest request) {
        return forgotPasswordService.resetPassword(otp, request.getNewPassword());
    }
}
