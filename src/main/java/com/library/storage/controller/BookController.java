package com.library.storage.controller;

import com.library.storage.dto.BookDTO;
import com.library.storage.service.BookServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private BookServices bookServices;

    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    public ResponseEntity getBooks() {
        return ResponseEntity.ok(bookServices.getAllBooks());
    }

    @RequestMapping(value = "/api/authors/{authorId}/books", method = RequestMethod.POST)
    public ResponseEntity saveBook(@PathVariable Long authorId, @RequestBody BookDTO bookTitle) {
        return ResponseEntity.ok(bookServices.proceedBook(authorId, bookTitle));
    }


    @RequestMapping(value = "/api/books/{bookId}", method = RequestMethod.GET)
    public ResponseEntity getBookByID(@PathVariable Long bookId) {
        return bookServices.getBook(bookId) == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) : ResponseEntity.ok(bookServices.getBook(bookId));
    }

    @RequestMapping(value = "/api/authors/{authorId}/books", method = RequestMethod.GET)
    public ResponseEntity getBooks(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookServices.getBookByAuthor(authorId));
    }

    @RequestMapping(value = "/api/authors/{authorId}/books/{bookId}", method = RequestMethod.GET)
    public ResponseEntity getBookByAuthorAndId(@PathVariable Long authorId, @PathVariable Long bookId) {
        return bookServices.getBookByAuthorAndId(authorId, bookId) == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) :ResponseEntity.ok(bookServices.getBookByAuthorAndId(authorId, bookId));
    }

}
