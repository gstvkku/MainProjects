package io.project.dev.athens_library.controller;

import io.project.dev.athens_library.dto.ChangePassRequestDto;
import io.project.dev.athens_library.dto.UserDto;
import io.project.dev.athens_library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(201).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping("/{id}/change-pass")
    public ResponseEntity<String> updateUserPassword(@PathVariable Long id,
                                                     @RequestParam ChangePassRequestDto request) {
        String updatedPassword = userService.updateUserPass(id, request.getOldPass(), request.getNewPass());
        if (updatedPassword != null) {
            return ResponseEntity.ok(updatedPassword);
        }
        return ResponseEntity.status(400).build();
    }
}
