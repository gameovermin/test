package org.example.springbootDeveloper.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String book_title;

    @Column(nullable = false, length = 100)
    private String book_author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private BookCategory book_category;

}
