package com.gaon.bookmanagement.repository;

import com.gaon.bookmanagement.domain.BorrowBook;
import com.gaon.bookmanagement.dto.response.BooksRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBook, Long> {
}
