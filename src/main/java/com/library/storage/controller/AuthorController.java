package com.library.storage.controller;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.service.AuthorServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/authors")
public class AuthorController {

    private final AuthorServices authorServices;

    @PostMapping
    public ResponseEntity saveAuthor(@RequestBody AuthorDTO body) {
        long runTime = System.currentTimeMillis();
        String author = authorServices.processAuthor(body);
        runTime = System.currentTimeMillis() - runTime;
        log.debug("Time for saveAuthor {} ms", runTime);
        return ResponseEntity.ok(author);
    }

    @GetMapping
    public ResponseEntity getAuthors() {
        long runTime = System.currentTimeMillis();
        List<AuthorDTO> allAuthors = authorServices.getAllAuthors();
        runTime = System.currentTimeMillis() - runTime;
        log.debug("Time for getAuthors {} ms", runTime);
        return ResponseEntity.ok(allAuthors);
    }


    @GetMapping(value = "/{authorID}")
    public ResponseEntity getAuthor(@PathVariable Long authorID) {
        long runTime = System.currentTimeMillis();
        AuthorDTO author = authorServices.getAuthor(authorID);
        runTime = System.currentTimeMillis() - runTime;
        log.debug("Time for getAuthor {} ms", runTime);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(author);
        }
    }


}
