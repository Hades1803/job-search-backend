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
            System.out.println("Email sent! Status: " + response.getStatusCode());
        } catch (IOException ex) {
            System.out.println("❌ Failed to send email to " + to + ": " + ex.getMessage());
            throw new RuntimeException("Cannot send email via SendGrid API");
        }
    }
}
