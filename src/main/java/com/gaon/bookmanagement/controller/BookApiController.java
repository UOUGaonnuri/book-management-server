package com.gaon.bookmanagement.controller;

import com.gaon.bookmanagement.constant.dto.ApiResponse;
import com.gaon.bookmanagement.dto.request.BorrowReqDto;
import com.gaon.bookmanagement.dto.response.BookDetailRespDto;
import com.gaon.bookmanagement.dto.response.BooksRespDto;
import com.gaon.bookmanagement.dto.response.BorrowRespDto;
import com.gaon.bookmanagement.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 책 조회
    @GetMapping("/api/books")
    public ResponseEntity<ApiResponse<Page<BooksRespDto>>> getBooks(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum-1, 6, Sort.Direction.DESC, "id");
        Page<BooksRespDto> books = bookService.getBooks(pageable);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(books, "Get Books Success!"));
    }

    //책 상세 조회
    @GetMapping("/api/book/{bookId}")
    public ResponseEntity<ApiResponse<BookDetailRespDto>> getDetailBook(@PathVariable Long bookId) {
        BookDetailRespDto detailBook = bookService.getDetailBook(bookId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(detailBook, "Get Book Success!"));
    }

    // 책 대여
    @PostMapping("/api/user/book")
    public ResponseEntity<ApiResponse<List<BorrowRespDto>>> borrowBook(List<BorrowReqDto> borrowReqDtoList) {
        List<BorrowRespDto> borrowRespDtos = bookService.borrowBook(borrowReqDtoList);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(borrowRespDtos, "Book Borrow Success!"));
    }

    // 책 반납

    // 책 검색
    @GetMapping("/api/search")
    public ResponseEntity<ApiResponse<Page<BooksRespDto>>> searchBook(
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(value = "searchKeyword", defaultValue = "", required = false) String searchKeyword
    ) {
        Page<BooksRespDto> booksSearchDto;
        Pageable pageable = PageRequest.of(pageNum-1, 6, Sort.Direction.DESC, "id");

        if(searchKeyword.isEmpty()) {
            // 검색 키워드가 존재하지 않으면 모든 책 리턴
            booksSearchDto = bookService.getBooks(pageable);
        } else {
            booksSearchDto = bookService.BookSearchList(searchKeyword, pageable);
        }

        return ResponseEntity.ok().body(ApiResponse.createSuccess(booksSearchDto, "Book Search Success!"));
    }

}
