package org.example.springbootDeveloper.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springbootDeveloper.entity.Book;
import org.example.springbootDeveloper.entity.BookCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookResponseDto {
    @NotNull
    private Long id;
    @NotNull
    private String book_title;
    @NotNull
    private String book_author;
    @NotNull
    private BookCategory book_category;

    public GetBookResponseDto(Book book) {
        this.id = book.getId();
        this.book_title = book.getBook_title();
        this.book_author = book.getBook_author();
        this.book_category = book.getBook_category();
    }
}
