package com.gaon.bookmanagement.dto.request;

import com.gaon.bookmanagement.domain.BorrowBook;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BorrowReqDto {
    private Long bookId;
    private Long borrowDate;
}
