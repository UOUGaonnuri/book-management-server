package com.gaon.bookmanagement.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class BorrowBook extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime borrowDate;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID")
    private Book book;
    @Builder
    public BorrowBook(LocalDateTime expirationDate, Member member, Book book) {
        this.expirationDate = expirationDate;
        this.member = member;
        this.book = book;
    }

    public void addBorrowBook() {

    }

    public void setInitialExpired() {
        this.expired = false;
    }

    public void setExpired() {
        this.expired = true;
    }
}
