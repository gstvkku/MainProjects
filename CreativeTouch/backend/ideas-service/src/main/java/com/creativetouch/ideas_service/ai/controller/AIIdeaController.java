package com.creativetouch.ideas_service.ai.controller;

import com.creativetouch.ideas_service.ai.dto.AIIdeaRequest;
import com.creativetouch.ideas_service.ai.dto.AIIdeaResponse;
import com.creativetouch.ideas_service.ai.service.AIIdeaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIIdeaController {

    private final AIIdeaService aiIdeaService;

    @PostMapping("/generate/idea")
    public ResponseEntity<AIIdeaResponse> generateIdea(
            @Valid @RequestBody AIIdeaRequest request
    ) {

        AIIdeaResponse response = aiIdeaService.generate(request);

        return ResponseEntity.ok(response);
    }
}