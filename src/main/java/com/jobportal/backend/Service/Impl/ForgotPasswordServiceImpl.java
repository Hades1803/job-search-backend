package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ForgotPasswordRequest;
import com.jobportal.backend.Dto.ResetPasswordRequest;
import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.ForgotPassword;
import com.jobportal.backend.Exception.*;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Repository.ForgotPasswordRepo;
import com.jobportal.backend.Service.EmailService;
import com.jobportal.backend.Service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final AccountRepo accountRepo;
    private final ForgotPasswordRepo forgotPasswordRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final long OTP_EXPIRATION_MS = 5 * 60 * 1000;

    @Override
    public String sendOtp(ForgotPasswordRequest request) {
        Account account = accountRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email không tồn tại"));

        int otp = new Random().nextInt(900000) + 100000;

        ForgotPassword forgotPassword = forgotPasswordRepo.findByAccount(account)
                .orElse(new ForgotPassword());

        forgotPassword.setAccount(account);
        forgotPassword.setOtp(otp);
        forgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + OTP_EXPIRATION_MS));

        forgotPasswordRepo.save(forgotPassword);

        emailService.sendEmail(account.getEmail(), "OTP reset mật khẩu", "Mã OTP của bạn là: " + otp);

        return "OTP đã được gửi tới email của bạn";
    }

    @Override
    public String verifyOtp(int otp) {
        ForgotPassword fp = forgotPasswordRepo.findByOtp(otp)
                .orElseThrow(() -> new OtpInvalidException("OTP không đúng"));

        if (fp.getExpirationTime().before(new Date())) {
            throw new OtpExpiredException("OTP đã hết hạn");
        }

        // Có thể đánh dấu OTP đã verify nếu muốn
        fp.setVerified(true);
        forgotPasswordRepo.save(fp);

        return "OTP hợp lệ";
    }

    @Override
    public String resetPassword(int otp, String newPassword) {
        // Lấy bản ghi OTP
        ForgotPassword fp = forgotPasswordRepo.findByOtp(otp)
                .orElseThrow(() -> new OtpInvalidException("OTP không đúng"));

        // Kiểm tra OTP đã verify chưa
        if (fp.getExpirationTime().before(new Date())) {
            throw new OtpExpiredException("OTP đã hết hạn");
        }

        if (fp.getVerified() == null || !fp.getVerified()) {
            throw new OtpNotVerifiedException("OTP chưa được xác thực");
        }

        Account account = fp.getAccount();
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepo.save(account);

        // Xóa OTP sau khi dùng
        forgotPasswordRepo.delete(fp);

        return "Đổi mật khẩu thành công";
    }

}
