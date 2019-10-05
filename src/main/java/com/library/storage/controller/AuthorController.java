package com.library.storage.controller;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.service.AuthorServices;
import com.library.storage.service.BookServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    private AuthorServices authorServices;

    public AuthorController(AuthorServices authorServices) {
        this.authorServices = authorServices;
    }

    @RequestMapping(value = "/api/authors", method = RequestMethod.POST)
    public ResponseEntity saveAuthor(@RequestBody AuthorDTO body) {
        return ResponseEntity.ok(authorServices.processAuthor(body));
    }

    @RequestMapping(value = "/api/authors", method = RequestMethod.GET)
    public ResponseEntity getAuthors() {
        return ResponseEntity.ok(authorServices.getAllAuthors());
    }

    @RequestMapping(value = "/api/authors/{authorId}/books", method = RequestMethod.GET)
    public ResponseEntity getBooks(@PathVariable String authorId) {
        return ResponseEntity.ok(authorId);
    }

    @RequestMapping(value = "/api/authors/{authorId}/books", method = RequestMethod.POST)
    public ResponseEntity saveBook(@PathVariable String authorId) {
        return ResponseEntity.ok(authorId);
    }

    @RequestMapping(value = "/api/authors/{authorID}", method = RequestMethod.GET)
    public ResponseEntity getAuthor(@PathVariable Long authorID) {
        return ResponseEntity.ok(authorServices.getAuthor(authorID));
    }


    @RequestMapping(value = "/api/authors/{authorId}/books/{bookId}", method = RequestMethod.GET)
    public ResponseEntity getBookByAuthor(@PathVariable String authorId, @PathVariable String bookId) {
        return ResponseEntity.ok(authorId + " " + bookId);
    }

}
