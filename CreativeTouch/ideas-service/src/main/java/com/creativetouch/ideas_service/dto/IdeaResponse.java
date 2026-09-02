package com.creativetouch.ideas_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class IdeaResponse {

    private UUID id;
    private UUID userId;
    private String title;
    private String niche;
    private String description;
    private String observation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}