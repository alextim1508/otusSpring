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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @Before
    public void initMock() {
        when(personService.add(any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String name  = (String)args[0];
            Person pers = new Person(name);
            if(name.equals("Fedor"))
                throw new RuntimeException("Запись Fedor существует");
            return pers;
        });

        when(personService.findById(any(Long.class))).thenReturn(new Person("Sergey"));

        when(personService.update(any(Long.class), any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String name = (String) args[1];
            return new Person(name);
        });
    }

    @Test
    public void insertTest() throws Exception {
        String name = "Alex";
        mvc.perform(post("/person/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"" + name + "\" }"))
                .andExpect(status().is(SC_CREATED))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void saveTest() throws Exception {
        String id = "1";
        String name = "Alex";
        mvc.perform(post("/person/save/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"" + name + "\" }"))
                .andExpect(status().is(SC_ACCEPTED))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void handleExceptionTest() throws Exception {
        String name = "Fedor";
        this.mvc.perform(post("/person/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"" + name + "\" }"))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andExpect(content().string(containsString("Запись Fedor существует")));
    }
}