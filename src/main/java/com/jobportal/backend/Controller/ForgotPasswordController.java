package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ForgotPasswordRequest;
import com.jobportal.backend.Dto.ResetPasswordRequest;
import com.jobportal.backend.Service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;


    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return forgotPasswordService.sendOtp(request);
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody Map<String, Integer> request) {
        int otp = request.get("otp");
        return forgotPasswordService.verifyOtp(otp);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request) {
        int otp = request.getOtp(); // thêm trường otp trong DTO
        return forgotPasswordService.resetPassword(otp, request.getNewPassword());
    }
}
