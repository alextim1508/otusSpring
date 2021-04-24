package com.alextim.repository;

import com.alextim.config.ApplicationSettings;
import com.alextim.config.Config;
import com.alextim.domain.Question;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Repository
@Data @RequiredArgsConstructor @Slf4j
public class InterviewRepositoryImpl implements InterviewRepository {

    private static final String QUESTION_SPLITTER = ";";

    private final MessageSource messageSource;
    private final ApplicationSettings settings;

    private List<Question> questions;
    private List<String> greeting;
    private List<String> results;

    private static final String PARSING_ERROR = "Questions load exception";

    @Override
    public void load() {
        Locale locale = null;
        if(settings.isUseEnglishLocal())
            locale = new Locale("en", "US");

        questions = loadQuestions(settings.getQuestionCount(), messageSource, locale);
        greeting = loadGreeting(messageSource, locale);
        results = loadResults(messageSource, locale);
    }

    private List<String> loadGreeting(MessageSource messageSource, Locale locale)  {
        return Arrays.asList(
                messageSource.getMessage("your_name", null, locale),
                messageSource.getMessage("hello", null, locale));
    }

    private List<String> loadResults(MessageSource messageSource, Locale locale)  {
        return Arrays.asList(
                messageSource.getMessage("true", null, locale),
                messageSource.getMessage("false", null, locale),
                messageSource.getMessage("result", null, locale));
    }

    private List<Question> loadQuestions(int questionCount, MessageSource messageSource, Locale locale)  {
        List<Question> questions = new ArrayList<>();
        for (int i = 1; i < questionCount + 1; i++) {
            String[] split = messageSource.getMessage("question" + i, null, locale).split(QUESTION_SPLITTER);
            System.out.println(Arrays.toString(split));
            if (split.length == 2) {
                questions.add(new Question(split[0], split[1]));
            } else {
                log.error("{} : {}", Arrays.toString(split), PARSING_ERROR);
                throw new RuntimeException(Arrays.toString(split)+ " " + PARSING_ERROR);
            }
        }
        return questions;
    }
}
