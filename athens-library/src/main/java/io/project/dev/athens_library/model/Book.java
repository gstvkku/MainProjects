package io.project.dev.athens_library.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(unique = true)
    private String isbn;

    @Column(name = "available_copies")
    private int availableCopies;

    @OneToMany(mappedBy = "book")
    private List<Reservation> reservations;
}
