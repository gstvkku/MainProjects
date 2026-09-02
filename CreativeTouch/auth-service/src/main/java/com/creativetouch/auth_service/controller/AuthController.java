package com.creativetouch.auth_service.controller;

import com.creativetouch.auth_service.config.TokenConfig;
import com.creativetouch.auth_service.dto.request.LoginRequest;
import com.creativetouch.auth_service.dto.request.RegisterRequest;
import com.creativetouch.auth_service.dto.response.LoginResponse;
import com.creativetouch.auth_service.dto.response.RegisterResponse;
import com.creativetouch.auth_service.model.User;
import com.creativetouch.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        User user = authService.register(
                request.name(),
                request.email(),
                request.password()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new RegisterResponse(
                                user.getName(),
                                user.getEmail()
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                );

        Authentication authentication =
                authenticationManager.authenticate(credentials);

        User user = (User) authentication.getPrincipal();

        String token = tokenConfig.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }

    @PutMapping("/language-preference")
    public ResponseEntity<Void> updatePreferences(
            @RequestParam String languagePreference,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        authService.updateLanguagePreference(userId, languagePreference);
        return ResponseEntity.noContent().build();
    }
}

