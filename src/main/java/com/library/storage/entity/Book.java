package com.library.storage.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "book_title", nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}