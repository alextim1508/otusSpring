package com.alextim.repository;

import com.alextim.domain.Question;

import java.util.List;

public interface InterviewRepository {
    List<Question> getQuestions();
}
