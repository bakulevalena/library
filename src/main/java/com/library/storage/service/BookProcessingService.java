package com.library.storage.service;

import com.library.storage.dto.BookDTO;
import com.library.storage.entity.Author;
import com.library.storage.entity.Book;
import com.library.storage.repository.AuthorRepository;
import com.library.storage.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookProcessingService implements BookServices {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<BookDTO> getAllBooks() {
        log.debug("Getting all the books");
        List<Book> allBooks = bookRepository.findAll();
        List<BookDTO> resultList = new ArrayList<>(allBooks.size());
        for (Book book : allBooks) {
            BookDTO temp = new BookDTO();
            temp.setTitle(book.getTitle());
            temp.setAuthor(book.getAuthor().getAuthorName());
            resultList.add(temp);
        }
        if (allBooks.isEmpty()) {
            log.debug("No books here");
        } else {
            log.debug("Get all the books are finished");
        }
        return resultList;
    }

    @Override
    public BookDTO getBook(Long bookID) {
        log.debug("Getting the book by ID");
        BookDTO bookDTO = new BookDTO();
        Book book = bookRepository.findBookById(bookID);
        if (book != null) {
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor().getAuthorName());
        } else {
            log.warn("Unable to ger the book by ID {}", bookID);
            return null;
        }
        log.debug("The book {} is gotten", bookID);
        return bookDTO;
    }

    public void addBook(Book book, Author author) {
        author.getBooks().add(book);
        book.setAuthor(author);
    }

    public void removeBook(Book book, Author author) {
        author.getBooks().remove(book);
        book.setAuthor(null);
    }

    @Override
    @Transactional
    public String proceedBook(Long authorID, BookDTO book) {
        log.debug("Saving the book");
        Book savedBook = new Book();
        Author author = authorRepository.findAuthorById(authorID);
        if (author != null) {
            savedBook.setTitle(book.getTitle());
            savedBook.setAuthor(author);
            savedBook.setCreationDate(LocalDateTime.now());
            addBook(savedBook, author);
            bookRepository.save(savedBook);
            log.debug("The book of author {} is saved", authorID);
            return "Saved";

        } else {
            log.warn("Unable to save the book of author {}", authorID);
            return "This author is not exist";
        }
    }

    @Override
    public List<BookDTO> getBookByAuthor(Long authorId) {
        log.debug("Getting the book by the author ID");
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
            log.debug("The book of author {} is gotten", author);
            return resultList;
        } else {
            log.warn("Unable to ger book by author ID {}", authorId);
            return null;
        }
    }

    @Override
    public String getAllBooksString() {
        StringBuilder result = new StringBuilder();
        List<BookDTO> bookDTOList = getAllBooks();
        if (bookDTOList.isEmpty()) {
            result.append("No books here");
        } else {
            for (BookDTO book : bookDTOList) {
                result.append(book.getTitle());
                result.append(" - ");
                result.append(book.getAuthor());
                result.append('\n');
            }
        }
        return result.toString();
    }

    @Override
    public BookDTO getBookByAuthorAndId(Long authorId, Long bookId) {
        log.debug("Getting the book by the author ID and book ID");
        Book book = bookRepository.findBookById(bookId);
        if (book != null && book.getAuthor().getId().equals(authorId)) {
            BookDTO result = new BookDTO();
            result.setTitle(book.getTitle());
            result.setAuthor(book.getAuthor().getAuthorName());
            log.debug("The book {} of author {} is gotten", authorId, bookId);
            return result;
        } else {
            log.warn("Unable to get book by author ID {} and book ID {}", authorId, bookId);
            return null;
        }
    }
}
