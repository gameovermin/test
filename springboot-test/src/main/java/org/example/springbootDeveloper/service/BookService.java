package org.example.springbootDeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootDeveloper.common.ResponseMessage;
import org.example.springbootDeveloper.dto.response.GetBookListResponseDto;
import org.example.springbootDeveloper.dto.response.GetBookResponseDto;
import org.example.springbootDeveloper.dto.response.ResponseDto;
import org.example.springbootDeveloper.entity.Book;
import org.example.springbootDeveloper.reposiroty.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;


    public ResponseDto<List<GetBookListResponseDto>> getAllBooks() {
        List<GetBookListResponseDto> data = null;

        try {
            List<Book> books = bookRepository.findAll();

            data = books.stream()
                    .map((book) -> new GetBookListResponseDto(book))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    public ResponseDto<GetBookResponseDto> getBookById(Long id) {
        GetBookResponseDto data = null;
        Long bookId = id;

        try {
            Optional<Book> bookOptional = bookRepository.findById(bookId);

            if (bookOptional.isPresent())
                data = new GetBookResponseDto(bookOptional.get());
            else
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}
