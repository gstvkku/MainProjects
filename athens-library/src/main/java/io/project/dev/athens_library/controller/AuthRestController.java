package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.LoginRequestDto;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        UserDto userDto = authService.authLoginRequest(loginRequestDto);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }
}
