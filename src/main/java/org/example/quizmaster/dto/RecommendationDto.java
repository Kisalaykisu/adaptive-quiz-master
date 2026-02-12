package org.example.quizmaster.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationDto {
    private String recommendations;
    private List<String> suggestedQuestions;
}