package com.library.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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