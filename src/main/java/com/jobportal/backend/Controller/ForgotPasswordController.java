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

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return forgotPasswordService.sendOtp(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request) {
        return forgotPasswordService.resetPassword(request);
    }
}
