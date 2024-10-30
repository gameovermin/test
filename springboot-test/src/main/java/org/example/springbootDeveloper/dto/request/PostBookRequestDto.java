package org.example.springbootDeveloper.dto.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springbootDeveloper.entity.BookCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostBookRequestDto {
    private String book_title;
    private String book_author;
    private BookCategory book_category;
}
