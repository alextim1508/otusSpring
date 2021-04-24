package com.alextim;

import com.alextim.service.InterviewService;
import com.alextim.service.InterviewServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        InterviewService service = context.getBean(InterviewServiceImpl.class);
        service.interview();
    }
}
