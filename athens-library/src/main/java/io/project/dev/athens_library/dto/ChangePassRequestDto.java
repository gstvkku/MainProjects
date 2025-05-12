package io.project.dev.athens_library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePassRequestDto {
    private String oldPass;
    private String newPass;
}
