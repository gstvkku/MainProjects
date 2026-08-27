package com.creativetouch.mail_service.controller;

import com.creativetouch.mail_service.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/welcome")
    public ResponseEntity<String> test(
            @RequestParam String email,
            @RequestParam String name
    ) throws MessagingException {

        mailService.sendWelcomeEmail(email, name);

        return ResponseEntity.ok("Email enviado!");
    }
}