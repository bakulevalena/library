package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.dto.BookDTO;
import com.library.storage.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookProcessingService implements BookServices {

    @Override
    public Long countBooks(AuthorDTO Author) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public Book getBook(Long authorID, Long bookID) {
        return null;
    }

    @Override
    public String proceedBook(AuthorDTO author) {
        return null;
    }

    @Override
    public Book getBookByAuthor(AuthorDTO author) {
        return null;
    }
}
