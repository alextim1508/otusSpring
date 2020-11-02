package com.alextim.repository;

import com.alextim.domain.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository @Slf4j
public class InterviewRepositoryImpl implements InterviewRepository {

    private final List<Question> questions;

    private static final String PARSING_ERROR = "Exception questions load from csv file";

    public InterviewRepositoryImpl(@Value("${application.file}") String fileNameFromResources) {
        try {
            questions = load(fileNameFromResources);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Question> load(String fileName) throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));

        List<Question> questions = new ArrayList<>();
        String line;
        while((line = reader.readLine()) != null) {
            String[] split = line.split(";");
            if (split.length == 2) {
                questions.add(new Question(split[0], split[1]));
            } else {
                log.error("{} : {}", Arrays.toString(split), PARSING_ERROR);
                throw new RuntimeException(Arrays.toString(split)+ " " + PARSING_ERROR);
            }
        }
        return questions;
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
