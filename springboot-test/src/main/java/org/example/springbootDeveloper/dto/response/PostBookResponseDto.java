package org.example.springbootDeveloper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springbootDeveloper.entity.BookCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBookResponseDto {
    private Long id;
    private String book_title;
    private String book_author;
    private BookCategory book_category;
}
