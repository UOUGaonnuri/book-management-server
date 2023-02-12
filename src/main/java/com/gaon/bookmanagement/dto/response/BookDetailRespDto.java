package com.gaon.bookmanagement.dto.response;

import com.gaon.bookmanagement.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailRespDto {
    private String title;

    private String author;

    private String publisher;

    private int stock;

    private String content;

    private String isbn;

    private String fileName;

    private String fileUrl;

    @Builder
    public BookDetailRespDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.stock = book.getStock();
        this.content = book.getContent();
        this.isbn = book.getIsbn();
        this.fileName = book.getFileName();
        this.fileUrl = book.getFileUrl();
    }
}
