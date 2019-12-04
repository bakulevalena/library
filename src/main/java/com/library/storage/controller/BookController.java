package com.library.storage.controller;

import com.library.storage.dto.BookDTO;
import com.library.storage.service.BookServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private BookServices bookServices;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    public ResponseEntity getBooks() {
        long runTime = System.currentTimeMillis();
        List<BookDTO> bookList = bookServices.getAllBooks();
        runTime = System.currentTimeMillis() - runTime;
        logger.debug("Time for getBooks {} ms", runTime);
        return ResponseEntity.ok(bookList);
    }

    @RequestMapping(value = "/api/authors/{authorId}/books", method = RequestMethod.POST)
    public ResponseEntity saveBook(@PathVariable Long authorId, @RequestBody BookDTO bookTitle) {
        long runTime = System.currentTimeMillis();
        String processed = bookServices.proceedBook(authorId, bookTitle);
        runTime = System.currentTimeMillis() - runTime;
        logger.debug("Time for saveBook {} ms", runTime);
        return ResponseEntity.ok(processed);
    }


    @RequestMapping(value = "/api/books/{bookId}", method = RequestMethod.GET)
    public ResponseEntity getBookByID(@PathVariable Long bookId) {
        long runTime = System.currentTimeMillis();
        BookDTO book = bookServices.getBook(bookId);
        runTime = System.currentTimeMillis() - runTime;
        logger.debug("Time for getBookById {} ms", runTime);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(book);
        }
    }

    @RequestMapping(value = "/api/authors/{authorId}/books", method = RequestMethod.GET)
    public ResponseEntity getBooks(@PathVariable Long authorId) {
        long runTime = System.currentTimeMillis();
        List<BookDTO> booksByAuthor = bookServices.getBookByAuthor(authorId);
        runTime = System.currentTimeMillis() - runTime;
        logger.debug("Time for getBooks {} ms", runTime);
        return ResponseEntity.ok(booksByAuthor);
    }

    @RequestMapping(value = "/api/authors/{authorId}/books/{bookId}", method = RequestMethod.GET)
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
