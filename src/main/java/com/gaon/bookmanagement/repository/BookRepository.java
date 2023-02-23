package com.gaon.bookmanagement.repository;

import com.gaon.bookmanagement.domain.Book;
import com.gaon.bookmanagement.dto.response.BooksRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);
    Page<Book> findByTitleContainingIgnoreCase(String searchKeyword, Pageable pageable);

}
