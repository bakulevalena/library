package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;

import java.util.List;

public interface AuthorServices {

    String processAuthor(AuthorDTO author);

    List<AuthorDTO> getAllAuthors();

    AuthorDTO getAuthor(Long id);

    String getAllAuthorsString();
}
