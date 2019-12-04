package com.library.storage.controller;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.service.AuthorServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private AuthorServices authorServices;

    public AuthorController(AuthorServices authorServices) {
        this.authorServices = authorServices;
    }

    @RequestMapping(value = "/api/authors", method = RequestMethod.POST)
    public ResponseEntity saveAuthor(@RequestBody AuthorDTO body) {
        long runTime = System.currentTimeMillis();
        String author = authorServices.processAuthor(body);
        runTime = System.currentTimeMillis() - runTime;
        logger.debug("Time for saveAuthor {} ms", runTime);
        return ResponseEntity.ok(author);
    }

    @RequestMapping(value = "/api/authors", method = RequestMethod.GET)
    public ResponseEntity getAuthors() {
        long runTime = System.currentTimeMillis();
        List<AuthorDTO> allAuthors = authorServices.getAllAuthors();
        runTime = System.currentTimeMillis() - runTime;
        logger.debug("Time for getAuthors {} ms", runTime);
        return ResponseEntity.ok(allAuthors);
    }


    @RequestMapping(value = "/api/authors/{authorID}", method = RequestMethod.GET)
    public ResponseEntity getAuthor(@PathVariable Long authorID) {
        long runTime = System.currentTimeMillis();
        AuthorDTO author = authorServices.getAuthor(authorID);
        runTime = System.currentTimeMillis() - runTime;
        logger.debug("Time for getAuthor {} ms", runTime);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(author);
        }
    }


}
