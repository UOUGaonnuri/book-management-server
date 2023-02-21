package com.gaon.bookmanagement.controller;

import com.gaon.bookmanagement.constant.dto.ApiResponse;
import com.gaon.bookmanagement.dto.request.BookPostReqDto;
import com.gaon.bookmanagement.dto.request.BorrowReqDto;
import com.gaon.bookmanagement.dto.response.BookDetailRespDto;
import com.gaon.bookmanagement.dto.response.BookPostRespDto;
import com.gaon.bookmanagement.dto.response.BorrowRespDto;
import com.gaon.bookmanagement.service.book.BookService;
import com.google.protobuf.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 책 조회

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

}
