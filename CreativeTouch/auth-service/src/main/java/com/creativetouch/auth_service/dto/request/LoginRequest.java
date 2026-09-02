package com.creativetouch.auth_service.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "Email address is mandatory!") String email,
                           @NotEmpty(message = "Password is mandatory!") String password) {
}
