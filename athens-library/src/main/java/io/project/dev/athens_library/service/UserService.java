package io.project.dev.athens_library.service;


import io.project.dev.athens_library.converter.UserConverter;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.model.User;
import io.project.dev.athens_library.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserConverter.toDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserConverter.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return UserConverter.toDto(savedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());

        return UserConverter.toDto(userRepository.save(existingUser));
    }

    @Transactional
    public String updateUserPass(Long id, String oldPass, String newPass) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user != null && user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
            userRepository.save(user);
            return newPass;
        }

        return null;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }

}
