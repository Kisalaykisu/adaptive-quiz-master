package org.example.quizmaster.dto;

import lombok.Data;

@Data
public class StartQuizResponse {
    private Long quizId;
    private QuestionDto firstQuestion;
}
