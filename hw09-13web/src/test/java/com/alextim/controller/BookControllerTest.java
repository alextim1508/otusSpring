package com.alextim.controller;

import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import com.alextim.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;

import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Before
    public void initMock() throws NoSuchFieldException, IllegalAccessException {
        Author author = new Author("Leo", "Tolstoy");
        Field idField = author.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.setLong(author, 1l);
        idField.setAccessible(false);

        Genre roman = new Genre("Roman");
        idField = roman.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.setLong(roman, 1l);
        idField.setAccessible(false);

        Book book = new Book("War and Peace", author, roman);
        idField = book.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.setLong(book, 1l);
        idField.setAccessible(false);

        when(bookService.add(any(String.class), any(Long.class), any(Long.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String titleBook  = (String)args[0];
            if(titleBook.equals("Idiot"))
                throw new RuntimeException("Запись Idiot существует");
            return book;
        });

        when(bookService.findById(any(Long.class))).thenReturn(book);

        when(bookService.update(any(Long.class), any(String.class), any(Long.class), any(Long.class))).thenReturn(book);
    }

    @Test
    public void insertTest() throws Exception {
        mvc.perform(post("/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\" : \"War and Peace\",  \"idAuthor\" : 1, \"idGenre\" : 1 }"))
                .andExpect(status().is(SC_CREATED))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("War and Peace"))
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.genreId").value(1));
    }

    @Test
    public void saveTest() throws Exception {
        String expectedMessage = "War and Peace";
        mvc.perform(put("/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\" : \"War and Peace\",  \"idAuthor\" : 1, \"idGenre\" : 1 }"))
                .andExpect(status().is(SC_ACCEPTED))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(expectedMessage))
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.genreId").value(1));
    }

    @Test
    public void handleExceptionTest() throws Exception {
        this.mvc.perform(post("/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\" : \"Idiot\",  \"idAuthor\" : 2, \"idGenre\" : 1 }"))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andExpect(content().string(containsString("Запись Idiot существует")));
    }
}