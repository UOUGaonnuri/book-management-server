package com.gaon.bookmanagement.service.book;


import com.gaon.bookmanagement.constant.enums.ErrorCode;
import com.gaon.bookmanagement.constant.exception.CustomBookException;
import com.gaon.bookmanagement.constant.exception.CustomMemberException;
import com.gaon.bookmanagement.domain.Book;
import com.gaon.bookmanagement.dto.request.BookPostReqDto;
import com.gaon.bookmanagement.dto.response.BookPostRespDto;
import com.gaon.bookmanagement.dto.response.FileDto;
import com.gaon.bookmanagement.repository.BookRepository;
import com.gaon.bookmanagement.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final FileService fileService;

    // 책 등록
    public BookPostRespDto bookPost(BookPostReqDto bookPostReqDto, MultipartFile file) {
        Book book = bookPostReqDto.toEntity();

        // 파일 처리
        FileDto fileDto = fileService.uploadFIle(file);
        //
        book.setDeleted();
        book.addFile(fileDto);

        Book saveBook = bookRepository.save(book);

        return new BookPostRespDto(saveBook);
    }

    // isbn 중복 조회
    public void isbnDuplicateCheck(String isbn) {
        boolean result = bookRepository.existsByIsbn(isbn);
        if(result) {
            throw new CustomBookException(ErrorCode.DUPLICATION_ISBN);
        }
    }

    // 책 수정
    public BookPostRespDto bookEdit(Long bookId, BookPostReqDto bookEditDto, MultipartFile file) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new IllegalArgumentException("CAN NOT FIND");
        });

        // dto not null, file null
        if(bookEditDto != null && file == null) {
            book.editBook(bookEditDto);
        }
        // dto not null, file not null
        if(bookEditDto != null && file != null) {
            book.editBook(bookEditDto);
            FileDto fileDto = fileService.uploadFIle(file);
            book.addFile(fileDto);
        }
        // dto null, file not null
        if(bookEditDto == null && file != null) {
            FileDto fileDto = fileService.uploadFIle(file);
            book.addFile(fileDto);
        }

        return new BookPostRespDto(book);
    }

    //책 조회

    // 책 상세 조회

    // 책 삭제
    public void bookDelete(Long bookId) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new IllegalArgumentException("CAN NOT FIND");
        });

        findBook.bookDeleted();
        // 나중에 유저 찜 리스트에서 삭제하는 것도 리팩토링 해주어야함.
    }
}
