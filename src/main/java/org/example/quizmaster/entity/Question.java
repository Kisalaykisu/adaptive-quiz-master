package org.example.quizmaster.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private String topic;
    private String difficulty;
    private String modelAnswerHint;

    @JdbcTypeCode(SqlTypes.VECTOR)
    private float[] embedding; // 512-dim for nomic-embed-text
}
