package org.example.quizmaster.dto;

import lombok.Data;

@Data
public class SubmitAnswerRequest {
    private Long quizId;
    private Long questionId;
    private String userAnswer;
}
