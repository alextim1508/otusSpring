package com.alextim.service;

import com.alextim.domain.Question;
import com.alextim.repository.InterviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class InterviewServiceImplTest {

    @MockBean
    private InterviewRepository repository;

    @MockBean
    private InterviewService service;

    @Before
    public void initMock()  {
        when(repository.getQuestions()).thenReturn(Arrays.asList(
                new Question("1+1", "2"),
                new Question("1+2", "3"),
                new Question("2+2", "4"),
                new Question("3+3", "3")
                ));

        when(service.getAnswer(any(BufferedReader.class))).thenReturn("3");
    }

    @Test
    public void test() {
       Assertions.assertEquals(2, service.interview("Alex", 4));
    }
}
