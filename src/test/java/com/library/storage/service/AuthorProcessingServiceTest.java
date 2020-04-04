package com.library.storage.service;

import com.library.storage.dto.AuthorDTO;
import com.library.storage.entity.Author;
import com.library.storage.entity.Book;
import com.library.storage.repository.AuthorRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorProcessingServiceTest {

    private static final Long AUTHOR_ID = 1L;

    @Autowired
    private AuthorProcessingService authorProcessingService;
    @MockBean
    private AuthorRepository authorRepository;

    @Before
    public void init() {
        Book book = Book.builder()
                .id(1L)
                .title("Life of Anton")
                .creationDate(LocalDateTime.now())
                .build();
        Author author1 = Author.builder()
                .id(AUTHOR_ID)
                .authorName("Anton")
                .books(Collections.singletonList(book))
                .build();
        book.setAuthor(author1);
        Author author2 = Author.builder()
                .id(2L)
                .authorName("Ignat")
                .books(new ArrayList<>())
                .build();
        Mockito.doReturn(Arrays.asList(author1, author2))
                .when(authorRepository)
                .findAll();
        Mockito.doReturn(author1)
                .when(authorRepository)
                .findAuthorById(AUTHOR_ID);
    }

    @After
    public void reset() {
        Mockito.reset(authorRepository);
    }

    @Test
    public void processAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Anton");
        authorDTO.setBookCount(42);
        Author savedAuthor = new Author();
        savedAuthor.setAuthorName(authorDTO.getName());
        String actual = authorProcessingService.processAuthor(authorDTO);
        String expected = "Saved";

        Assert.assertEquals(expected, actual);
        Mockito.verify(authorRepository, Mockito.times(1))
                .save(savedAuthor);
    }

    @Test
    public void getAllAuthors() {
        AuthorDTO authorDTO = AuthorDTO.builder()
                .name("Anton")
                .bookCount(1)
                .build();
        AuthorDTO authorDTO2 = AuthorDTO.builder()
                .name("Ignat")
                .bookCount(0)
                .build();

        Assert.assertEquals(Arrays.asList(authorDTO, authorDTO2), authorProcessingService.getAllAuthors());
        Mockito.verify(authorRepository, Mockito.times(1))
                .findAll();

    }

    @Test
    public void getAuthor() {
        AuthorDTO actual = authorProcessingService.getAuthor(AUTHOR_ID);
        AuthorDTO expected = AuthorDTO.builder()
                .name("Anton")
                .bookCount(1)
                .build();

        Assert.assertEquals(expected, actual);
        Mockito.verify(authorRepository, Mockito.times(1))
                .findAuthorById(AUTHOR_ID);
    }
}