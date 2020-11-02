package com.alextim;

import com.alextim.service.InterviewService;
import com.alextim.service.InterviewServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@ComponentScan @Slf4j
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        InterviewService service = context.getBean(InterviewServiceImpl.class);
        service.interview();
    }
}
