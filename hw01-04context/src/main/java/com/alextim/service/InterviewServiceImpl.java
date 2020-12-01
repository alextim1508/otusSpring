package com.alextim.service;

import com.alextim.domain.Question;
import com.alextim.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class InterviewServiceImpl implements InterviewService{

    private final InterviewRepository repository;
    private final BufferedReader reader;
    private int score;

    @Override
    public int interview() {
        List<Question> questions = repository.getQuestions();
        Collections.shuffle(questions);

        List<String > greeting = repository.getGreeting();
        log.info(greeting.get(0));

        String userName = getAnswer(reader);
        log.info("{}, {}", greeting.get(1), userName);

        List<String> results = repository.getResults();
        for(int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            log.info("{}: ", question.getTitle());
            if(question.getAnswer().toLowerCase().equals(getAnswer(reader).toLowerCase())) {
                score++;
                log.info(results.get(0));
            } else {
                log.info(results.get(1));
            }
        }
        log.info("{}: {}/{}", results.get(2), score, questions.size());
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
