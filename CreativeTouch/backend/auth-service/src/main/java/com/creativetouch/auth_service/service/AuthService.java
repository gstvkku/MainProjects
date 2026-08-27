package com.creativetouch.auth_service.service;

import com.creativetouch.auth_service.event.UserEventProducer;
import com.creativetouch.auth_service.event.UserRegisteredEvent;
import com.creativetouch.auth_service.model.User;
import com.creativetouch.auth_service.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventProducer userEventProducer;

    public User register(String name, String email, String password) {

        User newUser = new User();

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));

        User savedUser = userRespository.save(newUser);

        userEventProducer.publishUserRegistered(
                new UserRegisteredEvent(
                        savedUser.getId(),
                        savedUser.getName(),
                        savedUser.getEmail()
                )
        );

        return savedUser;
    }
}
