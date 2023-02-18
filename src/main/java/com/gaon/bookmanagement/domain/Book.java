package com.gaon.bookmanagement.domain;

import com.gaon.bookmanagement.dto.request.BookPostReqDto;
import com.gaon.bookmanagement.dto.response.FileDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Book extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private boolean deleted;

//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BorrowBook> borrowBookList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<ZzimBook> zzimBookList = new ArrayList<>();

    @Builder
    public Book(String title, String author, String publisher, int stock, String content, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.stock = stock;
        this.content = content;
        this.isbn = isbn;
    }

    public void setDeleted() {
        this.deleted = false;
    }
    public void deletedBook() {
        this.deleted = true;
    }

    public void editBook(BookPostReqDto bookEditDto) {
        this.title = bookEditDto.getTitle();
        this.author = bookEditDto.getAuthor();
        this.publisher = bookEditDto.getPublisher();
        this.stock = bookEditDto.getStock();
        this.content = bookEditDto.getContent();
        this.isbn = bookEditDto.getIsbn();
    }

    public void addFile(FileDto fileDto) {
        this.fileName = fileDto.getFileName();
        this.fileUrl = fileDto.getFileUrl();
    }

    public void stockMinus() {
        if(this.stock < 1) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock --;
    }

    public void stockAdd() {
        this.stock ++;
    }
}
