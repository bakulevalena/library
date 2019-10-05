package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.entity.Book;

import java.util.List;

public interface BookServices {
    Long countBooks(AuthorDTO Author);

    List<Book> getAllBooks();

    Book getBook(Long authorID, Long bookID);

    String proceedBook(AuthorDTO author);

    Book getBookByAuthor(AuthorDTO author);
}
