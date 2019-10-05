package com.library.storage.repository;

import com.library.storage.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findBooksAndAuthor();
    List<Book> findBookByAuthor(String author);
    Long countBookByAuthor(String Author);
}
