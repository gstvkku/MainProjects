package io.project.dev.athens_library.service;

import io.project.dev.athens_library.converter.UserConverter;
import io.project.dev.athens_library.dto.LoginRequestDto;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.model.User;
import io.project.dev.athens_library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public UserDto authLoginRequest(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user != null && user.getPassword().equals(loginRequestDto.getPassword())) {
            return UserConverter.toDto(user);
        }

        throw new RuntimeException("Incorrect password");
    }

}
