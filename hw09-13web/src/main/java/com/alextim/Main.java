package com.alextim;

import com.alextim.controller.BookController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}