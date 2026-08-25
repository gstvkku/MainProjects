package com.creativetouch.auth_service.repository;

import com.creativetouch.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRespository extends JpaRepository<User, UUID> {
    Optional<UserDetails> findUserByEmail(String username);
}
