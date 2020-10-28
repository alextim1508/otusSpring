package com.alextim.service;


import com.alextim.domain.Question;
import com.alextim.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RequiredArgsConstructor @Slf4j
public class InterviewServiceImpl implements InterviewService{

    private final InterviewRepository repository;
    private int score;

    @Override
    public int interview(String userName, int amountQuestions) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Question> questions = repository.getQuestions();
        Collections.shuffle(questions);

        log.info("Hello, {}", userName);
        for(int i = 0; i < amountQuestions; i++) {
            Question question = questions.get(i);
            log.info("{}: ", question.getTitle());
            if(question.getAnswer().toLowerCase().equals(getAnswer(reader).toLowerCase())) {
                score++;
                log.info("Right!");
            } else {
                log.info("False");
            }
        }
        log.info("Result: {}/{}", score, amountQuestions);
        return score;
    }

    @Override
    public String getAnswer(BufferedReader reader) {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }
}
