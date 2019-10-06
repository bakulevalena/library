package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.dto.BookDTO;
import com.library.storage.entity.Book;

import java.util.List;

public interface BookServices {

    List<BookDTO> getAllBooks();

    BookDTO getBook(Long bookID);

    String proceedBook(Long authorID, BookDTO bookTitle);

    List<BookDTO> getBookByAuthor(Long authorId);

    BookDTO getBookByAuthorAndId(Long authorId, Long bookId);
}
