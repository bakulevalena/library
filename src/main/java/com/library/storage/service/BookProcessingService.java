package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.dto.BookDTO;
import com.library.storage.entity.Author;
import com.library.storage.entity.Book;
import com.library.storage.repository.AuthorRepository;
import com.library.storage.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookProcessingService implements BookServices {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookProcessingService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        List<BookDTO> resultList = new ArrayList<>(allBooks.size());
        for (Book book : allBooks) {
            BookDTO temp = new BookDTO();
            temp.setTitle(book.getTitle());
            temp.setAuthor(book.getAuthor().getAuthorName());
            resultList.add(temp);
        }
        return resultList;
    }

    @Override
    public BookDTO getBook(Long bookID) {
        BookDTO bookDTO = new BookDTO();
        Book book = bookRepository.findBookById(bookID);
        if (book != null) {
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor().getAuthorName());
        } else {
            return null;
        }
        return bookDTO;
    }

    @Override
    public String proceedBook(Long authorID, BookDTO book) {
        Book savedBook = new Book();
        Author author = authorRepository.findAuthorById(authorID);
        if (author != null) {
            savedBook.setTitle(book.getTitle());
            savedBook.setAuthor(author);
            savedBook.setCreationDate(LocalDateTime.now());
            author.addBook(savedBook);
            bookRepository.save(savedBook);
            return "Saved";

        } else {
            return "This author is not exist";
        }
    }

    @Override
    public List<BookDTO> getBookByAuthor(Long authorId) {
        Author author = authorRepository.findAuthorById(authorId);
        if (author != null) {
            List<Book> allBooks = bookRepository.findBookByAuthor(author);
            List<BookDTO> resultList = new ArrayList<>(allBooks.size());
            for (Book book : allBooks) {
                BookDTO temp = new BookDTO();
                temp.setTitle(book.getTitle());
                temp.setAuthor(book.getAuthor().getAuthorName());
                resultList.add(temp);
            }
            return resultList;
        } else {
            return null;
        }
    }

    @Override
    public BookDTO getBookByAuthorAndId(Long authorId, Long bookId) {
        Book book = bookRepository.findBookById(bookId);
        if (book != null && book.getAuthor().getId().equals(authorId)) {
            BookDTO result = new BookDTO();
            result.setTitle(book.getTitle());
            result.setAuthor(book.getAuthor().getAuthorName());
            return result;
        } else {
            return null;
        }
    }
}
