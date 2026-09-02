package com.creativetouch.ideas_service.ai.service;

import com.creativetouch.ideas_service.ai.dto.AIIdeaRequest;
import com.creativetouch.ideas_service.ai.dto.AIIdeaResponse;
import com.creativetouch.ideas_service.ai.prompt.AIIdeaPrompt;
import tools.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIIdeaService {

    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper;

    public AIIdeaResponse generate(AIIdeaRequest request) {

        String prompt = AIIdeaPrompt.GENERATE_IDEA
                .formatted(
                        request.language(),
                        request.niche()
                );

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model(ChatModel.GPT_5_2)
                .input(prompt)
                .build();

        Response response = openAIClient
                .responses()
                .create(params);

        String output = response.output()
                .stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(outputText -> outputText.text())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("OpenAI returned an empty response")
                );

        return objectMapper.readValue(
                output,
                AIIdeaResponse.class
        );
    }
}