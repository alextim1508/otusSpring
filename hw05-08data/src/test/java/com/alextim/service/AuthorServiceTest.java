package com.alextim.service;

import com.alextim.domain.Author;
import com.alextim.repository.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Before
    public void setUp() {
        doNothing().when(authorRepository).insert(any(Author.class));

        when(authorRepository.getCount()).thenAnswer(answer -> 10);

        when(authorRepository.getAll(any(Integer.class), any(Integer.class))).
                thenAnswer(answer -> Arrays.asList(
                        new Author("Александ", "Пушкин"),
                        new Author("Сергей", "Есенин")));

        when(authorRepository.findById(any(Long.class))).
                thenAnswer(answer -> new Author("Александр", "Пушкин"));

        doNothing().when(authorRepository).update(any(Long.class), any(Author.class));

        doNothing().when(authorRepository).delete(any(Long.class));
    }


    @Test
    public void checkInsertAuthor() throws Exception {
        authorService.add("Николай", "Гоголь");

        verify(authorRepository, times(1)).insert(any());
    }

}
