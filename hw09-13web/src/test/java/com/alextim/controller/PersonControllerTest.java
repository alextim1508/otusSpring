package com.alextim.controller;

import com.alextim.domain.Person;
import com.alextim.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@ContextConfiguration
@AutoConfigureMockMvc(secure = false)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @Before
    public void initMock() throws Exception {
        when(personService.add(any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String name  = (String)args[0];
            Person person = new Person(name);
            if(name.equals("Fedor"))
                throw new RuntimeException("Запись Fedor существует");
            return person;
        });

        when(personService.findById(any(Integer.class))).thenReturn(new Person("Sergey"));

        when(personService.update(any(Long.class), any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String name = (String) args[1];
            return new Person(name);
        });
    }

    @Test
    public void insertTest() throws Exception {
        mvc.perform(post("/person/insert")
                .param("name", "Alex"))
                .andExpect(status().is(SC_CREATED))
                .andExpect(content().string(containsString("Alex добавлен")));
    }

    @Test
    public void saveTest() throws Exception {
        mvc.perform(post("/person/save")
                .param("id", "1")
                .param("name", "Alex"))
                .andExpect(status().is(SC_ACCEPTED))
                .andExpect(content().string(containsString("Alex обновлен")));
    }

    @Test
    public void getErrorHtml() throws Exception {
        mvc.perform(post("/person/insert")
                .param("name", "Fedor"))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andExpect(content().string(containsString("Запись Fedor существует")));
    }


    @Test
    public void localizationTest() throws Exception {
        mvc.perform(get("/").locale(Locale.US))
                .andExpect(content().string(containsString("Library")));
    }

}