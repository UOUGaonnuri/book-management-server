package com.gaon.bookmanagement.dto.response;

import com.gaon.bookmanagement.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookPostRespDto {

    private Long id;

    private String title;

    private String author;

    private String publisher;

    private int stock;

    private String isbn;

    @Builder
    public BookPostRespDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.stock = book.getStock();
        this.isbn = book.getIsbn();
    }
}
