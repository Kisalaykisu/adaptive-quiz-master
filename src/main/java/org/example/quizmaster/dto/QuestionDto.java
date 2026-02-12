package org.example.quizmaster.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String text;
    private String topic;
    private String difficulty;
}
