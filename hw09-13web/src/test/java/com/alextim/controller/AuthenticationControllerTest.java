package com.alextim.controller;


import com.alextim.security.configuration.AuthenticationManagerImpl;
import com.alextim.security.configuration.PasswordEncoderImpl;
import com.alextim.security.configuration.SecurityConfiguration;
import com.alextim.service.working.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithAnonymousUser
    @Test
    public void testPublic() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testRedirectUnauthorized() throws Exception {
        mockMvc.perform(get("/authenticated")).andExpect(status().is3xxRedirection());
    }

    @WithMockUser
    @Test
    public void testAccessAuthorized() throws Exception {
        mockMvc.perform(get("/authenticated")).andExpect(status().isOk());
    }
}
