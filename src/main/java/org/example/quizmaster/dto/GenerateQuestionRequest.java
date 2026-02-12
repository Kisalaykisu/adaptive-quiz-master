package org.example.quizmaster.dto;

import lombok.Data;

@Data
public class GenerateQuestionRequest {
    private String topic;
    private String difficulty;
}
