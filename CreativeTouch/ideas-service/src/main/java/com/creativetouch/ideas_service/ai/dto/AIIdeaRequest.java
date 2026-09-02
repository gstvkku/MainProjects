package com.creativetouch.ideas_service.ai.dto;

import jakarta.validation.constraints.NotBlank;


public record AIIdeaRequest(

        @NotBlank
        String niche,

        @NotBlank
        String language

) {
}