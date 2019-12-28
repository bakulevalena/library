package com.library.storage.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "author_name", nullable = false)
    private String authorName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

}
