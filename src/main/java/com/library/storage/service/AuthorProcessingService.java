package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.entity.Author;
import com.library.storage.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorProcessingService implements AuthorServices {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public String processAuthor(AuthorDTO author) {
        log.debug("Saving the author");
        Author savedAuthor = new Author();
        savedAuthor.setAuthorName(author.getName());
        authorRepository.save(savedAuthor);
        log.debug("The author is saved");
        return "Saved";
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        log.debug("Getting all of authors");
        List<Author> allAuthors = authorRepository.findAll();
        List<AuthorDTO> resultList = new ArrayList<>(allAuthors.size());
        for (Author author : allAuthors) {
            AuthorDTO temp = new AuthorDTO();
            temp.setName(author.getAuthorName());
            temp.setBookCount(author.getBooks().size());
            resultList.add(temp);
        }
        if (allAuthors.isEmpty()) {
            log.debug("No authors here");
        } else {
            log.debug("Authors are gotten");
        }
        return resultList;
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        log.debug("Getting the author by ID");
        AuthorDTO authorDTO = new AuthorDTO();
        Author author = authorRepository.findAuthorById(id);
        if (author != null) {
            authorDTO.setName(author.getAuthorName());
            authorDTO.setBookCount(author.getBooks().size());
        } else {
            log.warn("Unable to get the author by ID {}", id);
            return null;
        }
        log.debug("Author {} is gotten", id);
        return authorDTO;
    }

}
