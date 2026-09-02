package com.creativetouch.auth_service.service;

import com.creativetouch.auth_service.event.UserEventProducer;
import com.creativetouch.auth_service.event.UserRegisteredEvent;
import com.creativetouch.auth_service.model.User;
import com.creativetouch.auth_service.repository.UserRepository;
import com.creativetouch.exceptions.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventProducer userEventProducer;

    public User register(String name, String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(
                    "Este email já está cadastrado"
            );
        }

        User newUser = new User();

        newUser.setName(name);
        newUser.setLanguagePreference("en");
        newUser.setEmail(email);
        newUser.setPassword(
                passwordEncoder.encode(password)
        );

        User savedUser = userRepository.save(newUser);

        userEventProducer.publishUserRegistered(
                new UserRegisteredEvent(
                        savedUser.getId(),
                        savedUser.getName(),
                        savedUser.getEmail()
                )
        );

        return savedUser;
    }

    public User updateLanguagePreference(UUID userId, String languagePreference) {
        User user = userRepository.getReferenceById(userId);

        user.setLanguagePreference(languagePreference);

        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
