package com.alextim.service;

import com.alextim.domain.Author;
import com.alextim.repository.AuthorRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
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
        when(authorRepository.save(any(Author.class)))
                .thenAnswer((Answer<Author>) invocationOnMock -> (Author) invocationOnMock.getArguments()[0]);

        when(authorRepository.count()).thenReturn(10l);

        when(authorRepository.findAll(any(Sort.class))).
                thenAnswer(answer -> Arrays.asList(
                        new Author("Александ", "Пушкин"),
                        new Author("Сергей", "Есенин")));

        when(authorRepository.findById(any(ObjectId.class))).
                thenAnswer(answer -> new Author("Александр", "Пушкин"));

        when(authorRepository.save(any(Author.class)))
                .thenAnswer((Answer<Author>) invocationOnMock -> (Author) invocationOnMock.getArguments()[0]);

        doNothing().when(authorRepository).deleteById(any(ObjectId.class));

    }


    @Test
    public void checkInsertAuthor() throws Exception {
        authorService.add("Николай", "Гоголь");

        verify(authorRepository, times(1)).save(any());
    }

}
