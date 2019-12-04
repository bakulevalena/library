package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.entity.Author;
import com.library.storage.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorProcessingService implements AuthorServices {

    private static final Logger logger = LoggerFactory.getLogger(AuthorProcessingService.class);
    private AuthorRepository authorRepository;

    public AuthorProcessingService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public String processAuthor(AuthorDTO author) {
        logger.debug("Saving the author");
        Author savedAuthor = new Author();
        savedAuthor.setAuthorName(author.getName());
        authorRepository.save(savedAuthor);
        logger.debug("The author is saved");
        return "Saved";
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        logger.debug("Getting all of authors");
        List<Author> allAuthors = authorRepository.findAll();
        List<AuthorDTO> resultList = new ArrayList<>(allAuthors.size());
        for (Author author : allAuthors) {
            AuthorDTO temp = new AuthorDTO();
            temp.setName(author.getAuthorName());
            temp.setBookCount(author.getBooks().size());
            resultList.add(temp);
        }
        if (allAuthors.isEmpty()) {
            logger.debug("No authors here");
        } else {
            logger.debug("Authors are gotten");
        }
        return resultList;
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        logger.debug("Getting the author by ID");
        AuthorDTO authorDTO = new AuthorDTO();
        Author author = authorRepository.findAuthorById(id);
        if (author != null) {
            authorDTO.setName(author.getAuthorName());
            authorDTO.setBookCount(author.getBooks().size());
        } else {
            logger.warn("Unable to get the author by ID {}", id);
            return null;
        }
        logger.debug("Author {} is gotten", id);
        return authorDTO;
    }

}
