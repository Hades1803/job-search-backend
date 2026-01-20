package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Service.EmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final String FROM_EMAIL = System.getenv("MAIL_FROM");  // verified email
    private final String SENDGRID_API_KEY = System.getenv("SENDGRID_API_KEY");

    @Override
    public void sendEmail(String to, String subject, String content) {

        System.out.println("========== SEND EMAIL DEBUG ==========");
        System.out.println("MAIL_FROM = " + FROM_EMAIL);
        System.out.println("TO = " + to);
        System.out.println("SUBJECT = " + subject);
        System.out.println("SENDGRID_API_KEY exists = " + (SENDGRID_API_KEY != null));
        System.out.println("SENDGRID_API_KEY length = " +
                (SENDGRID_API_KEY != null ? SENDGRID_API_KEY.length() : "null"));
        System.out.println("======================================");

        Email from = new Email(FROM_EMAIL);
        Email toEmail = new Email(to);
        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, toEmail, emailContent);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("========== SENDGRID RESPONSE ==========");
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());
            System.out.println("======================================");

            // 🚨 BẮT BUỘC kiểm tra status
            if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
                throw new RuntimeException("SendGrid rejected email, status=" + response.getStatusCode());
            }

            System.out.println("✅ Email sent successfully");

        } catch (Exception ex) {
            System.out.println("❌ Failed to send email");
            ex.printStackTrace();
            throw new RuntimeException("Cannot send email via SendGrid API");
        }
    }

}
