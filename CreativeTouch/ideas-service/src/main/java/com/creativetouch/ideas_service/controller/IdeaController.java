package com.creativetouch.ideas_service.controller;

import com.creativetouch.ideas_service.dto.IdeaResponse;
import com.creativetouch.ideas_service.dto.SaveIdeaRequest;
import com.creativetouch.ideas_service.dto.UpdateIdeaRequest;
import com.creativetouch.ideas_service.service.IdeaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/idea")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;

    @GetMapping
    public List<IdeaResponse> findAll(
            Authentication authentication
    ) {
        UUID userId = getUserId(authentication);

        return ideaService.findAll(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdeaResponse create(
            @Valid @RequestBody SaveIdeaRequest request,
            Authentication authentication
    ) {
        UUID userId = getUserId(authentication);

        return ideaService.create(request, userId);
    }

    @PutMapping("/{id}")
    public IdeaResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateIdeaRequest request,
            Authentication authentication
    ) {
        UUID userId = getUserId(authentication);

        return ideaService.update(id, request, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        UUID userId = getUserId(authentication);

        ideaService.delete(id, userId);
    }

    private UUID getUserId(Authentication authentication) {
        return UUID.fromString(authentication.getName());
    }
}