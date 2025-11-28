package com.jobportal.backend.Service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}