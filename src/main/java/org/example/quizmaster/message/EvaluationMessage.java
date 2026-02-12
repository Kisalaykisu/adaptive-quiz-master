package org.example.quizmaster.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationMessage {
    private String questionText;
    private String userAnswer;
    private String modelAnswerHint;
    private Long attemptId;
}
