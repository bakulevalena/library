package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.entity.Author;
import com.library.storage.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorProcessingService implements AuthorServices {

    private AuthorRepository authorRepository;

    public AuthorProcessingService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public String processAuthor(AuthorDTO author) {
        Author savedAuthor = new Author();
        savedAuthor.setAuthorName(author.getName());
        authorRepository.save(savedAuthor);
        return "Saved";
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> allAuthors = authorRepository.findAll();
        List<AuthorDTO> resultList = new ArrayList<>(allAuthors.size());
        for (Author author : allAuthors) {
            AuthorDTO temp = new AuthorDTO();
            temp.setName(author.getAuthorName());
            resultList.add(temp);
        }
        return resultList;
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        AuthorDTO authorDTO = new AuthorDTO();
        Author author = authorRepository.findAuthorById(id);
        if (author != null) {
            authorDTO.setName(author.getAuthorName());
        } else {
            return null;
        }
        return authorDTO;
    }
}
