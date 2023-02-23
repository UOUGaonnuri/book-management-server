package com.gaon.bookmanagement.controller;

import com.gaon.bookmanagement.constant.dto.ApiResponse;
import com.gaon.bookmanagement.dto.request.BookPostReqDto;
import com.gaon.bookmanagement.dto.response.BookPostRespDto;
import com.gaon.bookmanagement.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class BookAdminApiController {

    private final BookService bookService;
    // 책 등록
    @PostMapping("/book")
    public ResponseEntity<ApiResponse<BookPostRespDto>> bookPost(
            @RequestPart(value = "bookPostReqDto") @Valid BookPostReqDto bookPostReqDto,
            @RequestPart(value = "file") MultipartFile file
    ) {
        BookPostRespDto bookPostRespDto = bookService.bookPost(bookPostReqDto, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(bookPostRespDto, "Book Post Success!"));
    }

    // 테스트용도 컨트롤러 삭제해야함
    @PostMapping("/book/test")
    public ResponseEntity<ApiResponse<BookPostRespDto>> bookPostTest(
            @RequestBody @Valid BookPostReqDto bookPostReqDto
    ) {

        BookPostRespDto bookPostRespDto = bookService.bookPostNoFile(bookPostReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(bookPostRespDto, "Book Post No file Success!"));
    }

    // isbn 중복 조회
    @GetMapping("/{isbn}/isbn")
    public ResponseEntity<ApiResponse<Boolean>> isbnDupCheck(@PathVariable String isbn) {
        bookService.isbnDuplicateCheck(isbn);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(Boolean.FALSE, "사용 가능한 ISBN 입니다."));
    }

    // 책 수정
    @PutMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<BookPostRespDto>> bookEdit(
            @PathVariable Long bookId,
            @RequestPart(value = "bookPostReqDto", required = false) @Valid BookPostReqDto bookPostReqDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        BookPostRespDto bookEditDto = bookService.bookEdit(bookId, bookPostReqDto, file);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(bookEditDto, "Book Edit Success!"));
    }

    // 책 삭제
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<Boolean>> bookDelete(@PathVariable Long bookId) {
        bookService.bookDelete(bookId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(Boolean.TRUE, "Book Delete Success!"));
    }

}
