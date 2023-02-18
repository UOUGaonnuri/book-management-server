package com.gaon.bookmanagement.dto.response;

import com.gaon.bookmanagement.domain.BorrowBook;
import lombok.Getter;

@Getter
public class BorrowRespDto {
    private String title;

    public BorrowRespDto(BorrowBook book) {
        this.title = book.getBook().getTitle();
    }
}
