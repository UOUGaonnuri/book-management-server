package com.gaon.bookmanagement.repository;

import com.gaon.bookmanagement.domain.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBook, Long> {
}
