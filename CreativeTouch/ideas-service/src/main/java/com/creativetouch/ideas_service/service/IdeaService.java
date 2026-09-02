package com.creativetouch.ideas_service.service;

import com.creativetouch.ideas_service.dto.IdeaResponse;
import com.creativetouch.ideas_service.dto.SaveIdeaRequest;
import com.creativetouch.ideas_service.dto.UpdateIdeaRequest;
import com.creativetouch.ideas_service.model.Idea;
import com.creativetouch.ideas_service.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdeaService {

    private final IdeaRepository ideaRepository;

    public List<IdeaResponse> findAll(UUID userId) {

        return ideaRepository.findAllByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public IdeaResponse findById(
            UUID id,
            UUID userId
    ) {

        Idea idea = findIdea(id);

        checkOwnership(idea, userId);

        return toResponse(idea);
    }

    public IdeaResponse create(
            SaveIdeaRequest request,
            UUID userId
    ) {

        Idea idea = Idea.builder()
                .userId(userId)
                .title(request.getTitle())
                .niche(request.getNiche())
                .description(request.getDescription())
                .observation(request.getObservation())
                .build();

        Idea savedIdea = ideaRepository.save(idea);

        return toResponse(savedIdea);
    }

    public IdeaResponse update(
            UUID id,
            UpdateIdeaRequest request,
            UUID userId
    ) {

        Idea idea = findIdea(id);

        checkOwnership(idea, userId);

        idea.setTitle(request.getTitle());
        idea.setNiche(request.getNiche());
        idea.setDescription(request.getDescription());
        idea.setObservation(request.getObservation());

        Idea updatedIdea = ideaRepository.save(idea);

        return toResponse(updatedIdea);
    }

    public void delete(
            UUID id,
            UUID userId
    ) {

        Idea idea = findIdea(id);

        checkOwnership(idea, userId);

        ideaRepository.delete(idea);
    }

    private Idea findIdea(UUID id) {

        return ideaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Idea not found"));
    }

    private void checkOwnership(
            Idea idea,
            UUID userId
    ) {

        if (!idea.getUserId().equals(userId)) {
            throw new RuntimeException(
                    "You do not have permission to access this idea"
            );
        }
    }

    private IdeaResponse toResponse(Idea idea) {

        return IdeaResponse.builder()
                .id(idea.getId())
                .userId(idea.getUserId())
                .title(idea.getTitle())
                .niche(idea.getNiche())
                .description(idea.getDescription())
                .observation(idea.getObservation())
                .createdAt(idea.getCreatedAt())
                .updatedAt(idea.getUpdatedAt())
                .build();
    }
}