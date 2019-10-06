package com.library.storage.controller;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.service.AuthorServices;
import org.springframework.http.HttpStatus;
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


    @RequestMapping(value = "/api/authors/{authorID}", method = RequestMethod.GET)
    public ResponseEntity getAuthor(@PathVariable Long authorID) {
        return authorServices.getAuthor(authorID) == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) : ResponseEntity.ok(authorServices.getAuthor(authorID));
    }


}
