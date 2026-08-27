package com.creativetouch.auth_service.controller;

import com.creativetouch.auth_service.config.TokenConfig;
import com.creativetouch.auth_service.dto.request.LoginRequest;
import com.creativetouch.auth_service.dto.request.RegisterRequest;
import com.creativetouch.auth_service.dto.response.LoginResponse;
import com.creativetouch.auth_service.dto.response.RegisterResponse;
import com.creativetouch.auth_service.model.User;
import com.creativetouch.auth_service.repository.UserRespository;
import com.creativetouch.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        UsernamePasswordAuthenticationToken mailAndPass =
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                );

        Authentication authentication =
                authenticationManager.authenticate(mailAndPass);

        User user = (User) authentication.getPrincipal();

        String token = tokenConfig.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        User newUser = authService.register(
                request.name(),
                request.email(),
                request.password()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new RegisterResponse(
                                newUser.getName(),
                                newUser.getEmail()
                        )
                );
    }
}