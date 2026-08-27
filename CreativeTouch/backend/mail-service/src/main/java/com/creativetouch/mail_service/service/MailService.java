package com.creativetouch.mail_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(
            String email,
            String name
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Welcome to CreativeTouch!");

        String html = """
        <html>
            <body>
                <h1>Welcome, %s! 🎉</h1>

                <p>
                    Your CreativeTouch account has been successfully created.
                </p>

                <p>
                    We're excited to have you with us.
                </p>

                <p>
                    If you have any questions or need assistance,
                    we're always here to help.
                </p>

                <br>

                <p>
                    Best regards,<br>
                    <strong>The CreativeTouch Team</strong>
                </p>

                <br>

                <p>
                    ✨Your next big idea is just one click away!
                </p>
            </body>
        </html>
        """.formatted(name);

        helper.setText(html, true);

        mailSender.send(message);
    }
}