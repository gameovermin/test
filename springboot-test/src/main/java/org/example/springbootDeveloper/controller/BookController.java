package org.example.springbootDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootDeveloper.common.ApiMappingPattern;
import org.example.springbootDeveloper.dto.response.GetBookListResponseDto;
import org.example.springbootDeveloper.dto.response.GetBookResponseDto;
import org.example.springbootDeveloper.dto.response.ResponseDto;
import org.example.springbootDeveloper.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BOOK)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private static final String GET_BOOK_ONE = "/{id}";

    //  전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<GetBookListResponseDto>>> getAllBooks() {
        ResponseDto<List<GetBookListResponseDto>> result = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //  단건 조회
    @GetMapping(GET_BOOK_ONE)
    public ResponseEntity<ResponseDto<GetBookResponseDto>> getBookById(@PathVariable Long id) {
        ResponseDto<GetBookResponseDto> result = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
