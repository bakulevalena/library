package com.library.storage.controller;

import com.library.storage.dto.BookDTO;
import com.library.storage.service.BookServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookServices bookServices;

    @GetMapping(value = "/api/books")
    public ResponseEntity getBooks() {
        long runTime = System.currentTimeMillis();
        List<BookDTO> bookList = bookServices.getAllBooks();
        runTime = System.currentTimeMillis() - runTime;
        log.debug("Time for getBooks {} ms", runTime);
        return ResponseEntity.ok(bookList);
    }

    @PostMapping(value = "/api/authors/{authorId}/books")
    public ResponseEntity saveBook(@PathVariable Long authorId, @RequestBody BookDTO bookTitle) {
        long runTime = System.currentTimeMillis();
        String processed = bookServices.proceedBook(authorId, bookTitle);
        runTime = System.currentTimeMillis() - runTime;
        log.debug("Time for saveBook {} ms", runTime);
        return ResponseEntity.ok(processed);
    }


    @GetMapping(value = "/api/books/{bookId}")
    public ResponseEntity getBookByID(@PathVariable Long bookId) {
        long runTime = System.currentTimeMillis();
        BookDTO book = bookServices.getBook(bookId);
        runTime = System.currentTimeMillis() - runTime;
        log.debug("Time for getBookById {} ms", runTime);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(book);
        }
    }

    @GetMapping(value = "/api/authors/{authorId}/books")
    public ResponseEntity getBooks(@PathVariable Long authorId) {
        long runTime = System.currentTimeMillis();
        List<BookDTO> booksByAuthor = bookServices.getBookByAuthor(authorId);
        runTime = System.currentTimeMillis() - runTime;
        log.debug("Time for getBooks {} ms", runTime);
        return ResponseEntity.ok(booksByAuthor);
    }

    @GetMapping(value = "/api/authors/{authorId}/books/{bookId}")
    public ResponseEntity getBookByAuthorAndId(@PathVariable Long authorId, @PathVariable Long bookId) {
        long runTime = System.currentTimeMillis();
        BookDTO book = bookServices.getBookByAuthorAndId(authorId, bookId);
        runTime = System.currentTimeMillis() - runTime;
        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(book);
        }
    }

}
