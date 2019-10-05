package com.library.storage.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "book_title", nullable = false)
    private String book;
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String bookName, Author author) {
        this.book = bookName;
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

}