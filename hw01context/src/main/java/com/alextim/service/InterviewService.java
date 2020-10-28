package com.alextim.service;

import java.io.BufferedReader;

public interface InterviewService {
    int interview(String name, int amountQuestions);
    String getAnswer(BufferedReader reader);
}
