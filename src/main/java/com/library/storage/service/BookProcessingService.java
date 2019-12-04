package com.library.storage.service;

import com.library.storage.dto.BookDTO;
import com.library.storage.entity.Author;
import com.library.storage.entity.Book;
import com.library.storage.repository.AuthorRepository;
import com.library.storage.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookProcessingService implements BookServices {

    private static final Logger logger = LoggerFactory.getLogger(BookProcessingService.class);
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookProcessingService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        logger.debug("Getting all the books");
        List<Book> allBooks = bookRepository.findAll();
        List<BookDTO> resultList = new ArrayList<>(allBooks.size());
        for (Book book : allBooks) {
            BookDTO temp = new BookDTO();
            temp.setTitle(book.getTitle());
            temp.setAuthor(book.getAuthor().getAuthorName());
            resultList.add(temp);
        }
        if (allBooks.isEmpty()) {
            logger.debug("No books here");
        } else {
            logger.debug("Get all the books are finished");
        }
        return resultList;
    }

    @Override
    public BookDTO getBook(Long bookID) {
        logger.debug("Getting the book by ID");
        BookDTO bookDTO = new BookDTO();
        Book book = bookRepository.findBookById(bookID);
        if (book != null) {
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor().getAuthorName());
        } else {
            logger.warn("Unable to ger the book by ID {}", bookID);
            return null;
        }
        logger.debug("The book {} is gotten", bookID);
        return bookDTO;
    }

    @Override
    @Transactional
    public String proceedBook(Long authorID, BookDTO book) {
        logger.debug("Saving the book");
        Book savedBook = new Book();
        Author author = authorRepository.findAuthorById(authorID);
        if (author != null) {
            savedBook.setTitle(book.getTitle());
            savedBook.setAuthor(author);
            savedBook.setCreationDate(LocalDateTime.now());
            author.addBook(savedBook);
            bookRepository.save(savedBook);
            logger.debug("The book of author {} is saved", authorID);
            return "Saved";

        } else {
            logger.warn("Unable to save the book of author {}", authorID);
            return "This author is not exist";
        }
    }

    @Override
    public List<BookDTO> getBookByAuthor(Long authorId) {
        logger.debug("Getting the book by the author ID");
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
            logger.debug("The book of author {} is gotten", author);
            return resultList;
        } else {
            logger.warn("Unable to ger book by author ID {}", authorId);
            return null;
        }
    }

    @Override
    public BookDTO getBookByAuthorAndId(Long authorId, Long bookId) {
        logger.debug("Getting the book by the author ID and book ID");
        Book book = bookRepository.findBookById(bookId);
        if (book != null && book.getAuthor().getId().equals(authorId)) {
            BookDTO result = new BookDTO();
            result.setTitle(book.getTitle());
            result.setAuthor(book.getAuthor().getAuthorName());
            logger.debug("The book {} of author {} is gotten", authorId, bookId);
            return result;
        } else {
            logger.warn("Unable to get book by author ID {} and book ID {}", authorId, bookId);
            return null;
        }
    }
}
