package io.project.dev.athens_library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDto {
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private int availableCopies;
}
