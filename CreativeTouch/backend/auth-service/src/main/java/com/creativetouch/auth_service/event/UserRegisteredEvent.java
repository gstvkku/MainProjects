package com.creativetouch.auth_service.event;

import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        String name,
        String email
) {
}
