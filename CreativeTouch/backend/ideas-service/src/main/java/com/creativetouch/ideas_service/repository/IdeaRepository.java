package com.creativetouch.ideas_service.repository;

import com.creativetouch.ideas_service.model.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IdeaRepository extends JpaRepository<Idea, UUID> {
    List<Idea> findAllByUserId(UUID userId);
}
