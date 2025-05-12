package io.project.dev.athens_library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private String password;
}
