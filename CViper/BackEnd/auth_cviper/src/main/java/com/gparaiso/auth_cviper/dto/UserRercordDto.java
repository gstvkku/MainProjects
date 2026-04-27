package com.gparaiso.auth_cviper.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRercordDto(@NotBlank String name,
                             @NotBlank @Email String email,
                             @NotBlank String password) {
}
