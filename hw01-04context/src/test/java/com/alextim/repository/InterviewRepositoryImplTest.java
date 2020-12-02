package com.alextim.repository;

import com.alextim.config.ApplicationSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterviewRepositoryImplTest {

    @MockBean
    private MessageSource messageSource;
    @MockBean
    private ApplicationSettings settings;
    @Autowired
    private InterviewRepositoryImpl repository;

    @Before
    public void initMock() {
        when(settings.isUseEnglishLocal()).thenReturn(true);
        when(settings.getQuestionCount()).thenReturn(5);
    }

    @Test
    public void test() {
        verify(messageSource, times(10)).getMessage(
                        any(String.class),  eq(null), eq(new Locale("en", "US")));
    }
}
