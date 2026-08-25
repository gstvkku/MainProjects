package com.creativetouch.auth_service.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(@NotEmpty(message = "Name is mandatory!") String name,
                              @NotEmpty(message = "Email address is mandatory!") String email,
                              @NotEmpty(message = "Password is mandatory!") String password) {
}
