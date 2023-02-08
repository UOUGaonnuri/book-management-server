package com.gaon.bookmanagement.repository;

import com.gaon.bookmanagement.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
