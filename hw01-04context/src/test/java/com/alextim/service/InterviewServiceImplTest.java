package com.alextim.service;

import com.alextim.domain.Question;
import com.alextim.repository.InterviewRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterviewServiceImplTest {

    @MockBean
    private InterviewRepository repository;
    @MockBean
    private BufferedReader reader;
    @Autowired
    private InterviewService service;

    @Before
    public void initMock() throws IOException {
        when(repository.getQuestions()).thenReturn(Arrays.asList(
                new Question("Russia", "Moscow"),
                new Question("Belgium", "Brussels"),
                new Question("Italy", "Rome"),
                new Question("Germany;", "Berlin")
        ));

        when(repository.getGreeting()).thenReturn(Arrays.asList("What is your name?", "Hello"));
        when(repository.getResults()).thenReturn(Arrays.asList("true", "false", "result"));
    }

    @Test
    public void test() throws IOException {
        when(reader.readLine()).thenAnswer(invocation-> "Moscow");
        Assert.assertEquals(1, service.interview());
    }
}
