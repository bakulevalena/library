package com.library.storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    public ResponseEntity getBooks() {
        return ResponseEntity.ok("books");
    }

    @RequestMapping(value = "/api/books/{bookId}", method = RequestMethod.GET)
    public ResponseEntity getBookByID(@PathVariable String bookId) {
        return ResponseEntity.ok(bookId);
    }

}
