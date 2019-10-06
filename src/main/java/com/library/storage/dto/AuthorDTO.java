package com.library.storage.dto;

public class AuthorDTO {
    private String name;
    private Integer bookCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Integer getBookCount() {
        return bookCount;
    }
}
