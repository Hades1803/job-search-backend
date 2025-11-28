package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            message.setFrom("your_email@gmail.com"); // Email gửi đi
            mailSender.send(message);
            System.out.println("✔ Email sent to " + to);
        } catch (Exception e) {
            System.out.println("❌ Failed to send email to " + to + ": " + e.getMessage());
            throw new RuntimeException("Cannot send email");
        }
    }
}
