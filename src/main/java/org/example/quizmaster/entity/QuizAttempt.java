package org.example.quizmaster.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class QuizAttempt {
    @Id
    @GeneratedValue
    private Long id;
    private Long quizId;

    @ManyToOne
    private Question question;

    private String userAnswer;
    private Double score;
    private String feedback;
    private Long userId;
}
