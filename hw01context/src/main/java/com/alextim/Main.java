package com.alextim;

import com.alextim.service.InterviewService;
import com.alextim.service.InterviewServiceImpl;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        InterviewService service = context.getBean(InterviewServiceImpl.class);
        service.interview("Ivan", 5);
    }
}
