package com.gaon.bookmanagement.dto.request;

import com.gaon.bookmanagement.domain.Book;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class BookPostReqDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "저자를 입력해주세요.")
    private String author;

    @NotBlank(message = "출판사를 입력해주세요.")
    private String publisher;

    @Min(value = 1, message = "하나 이상의 재고를 입력해주세요.")
    private int stock;

    @NotBlank(message = "책 내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "ISBN을 입력해주세요.")
    private String isbn;

    @Builder
    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .stock(stock)
                .content(content)
                .isbn(isbn)
                .build();
    }
}
