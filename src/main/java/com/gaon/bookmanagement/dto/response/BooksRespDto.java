package com.gaon.bookmanagement.dto.response;

import com.gaon.bookmanagement.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BooksRespDto {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String content;
    private String fileName;
    private String fileUrl;

    public BooksRespDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.content = book.getContent();
        this.content = book.getContent();
        this.fileName = book.getFileName();
        this.fileUrl = book.getFileUrl();
    }
}
