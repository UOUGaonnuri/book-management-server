package com.gaon.bookmanagement.service.book;


import com.gaon.bookmanagement.config.security.SecurityUtil;
import com.gaon.bookmanagement.constant.enums.ErrorCode;
import com.gaon.bookmanagement.constant.exception.CustomException;
import com.gaon.bookmanagement.domain.Book;
import com.gaon.bookmanagement.domain.BorrowBook;
import com.gaon.bookmanagement.domain.Member;
import com.gaon.bookmanagement.dto.request.BookPostReqDto;
import com.gaon.bookmanagement.dto.request.BorrowReqDto;
import com.gaon.bookmanagement.dto.response.BookDetailRespDto;
import com.gaon.bookmanagement.dto.response.BookPostRespDto;
import com.gaon.bookmanagement.dto.response.BorrowRespDto;
import com.gaon.bookmanagement.dto.response.FileDto;
import com.gaon.bookmanagement.repository.BookRepository;
import com.gaon.bookmanagement.repository.BorrowBookRepository;
import com.gaon.bookmanagement.repository.MemberRepository;
import com.gaon.bookmanagement.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final BorrowBookRepository borrowBookRepository;

    // 책 등록
    public BookPostRespDto bookPost(BookPostReqDto bookPostReqDto, MultipartFile file) {
        Book book = bookPostReqDto.toEntity();

        // 파일 처리
        FileDto fileDto = fileService.uploadFIle(file);

        book.addFile(fileDto);

        Book saveBook = bookRepository.save(book);

        return new BookPostRespDto(saveBook);
    }

    // isbn 중복 조회
    public void isbnDuplicateCheck(String isbn) {
        boolean result = bookRepository.existsByIsbn(isbn);
        if(result) {
            throw new CustomException(ErrorCode.DUPLICATION_ISBN);
        }
    }

    // 책 수정
    public BookPostRespDto bookEdit(Long bookId, BookPostReqDto bookEditDto, MultipartFile file) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FIND_BOOK);
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
    public BookDetailRespDto getDetailBook(Long bookId) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FIND_BOOK);
        });

        return new BookDetailRespDto(findBook);
    }


    // 책 삭제
    public void bookDelete(Long bookId) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FIND_BOOK);
        });

        findBook.deletedBook();
        // 나중에 유저 찜 리스트에서 삭제하는 것도 리팩토링 해주어야함.
    }

    // 책 빌리기
    public List<BorrowRespDto> borrowBook(List<BorrowReqDto> borrowReqDtoList) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMember()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FIND_USER);
        });
        LocalDate now = LocalDate.now();

        List<BorrowRespDto> borrowBookList = new ArrayList<>();

        for(BorrowReqDto borrowReqDto : borrowReqDtoList) {
            Book findBook = bookRepository.findById(borrowReqDto.getBookId()).orElseThrow(() -> {
                throw new CustomException(ErrorCode.NOT_FIND_BOOK);
            });

            LocalDateTime expirationDate = now.plusDays(borrowReqDto.getBorrowDate()).atStartOfDay();

            BorrowBook borrowBook = new BorrowBook(expirationDate, member, findBook);
            borrowBook.setInitialExpired();

            findBook.stockMinus();
            // 연관관계 편의메서드 만들기
            BorrowBook saveBorrowBook = borrowBookRepository.save(borrowBook);

            borrowBookList.add(new BorrowRespDto(saveBorrowBook));
        }

        return borrowBookList;
    }
}
