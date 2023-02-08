package com.gaon.bookmanagement.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int isbn;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowBook> borrowBookList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<ZzimBook> zzimBookList = new ArrayList<>();
}
