package org.example.quizmaster.repository;


import org.example.quizmaster.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.topic = ?1 AND q.difficulty = ?2 ORDER BY RANDOM() LIMIT 1")
    Question findRandomByTopicAndDifficulty(String topic, String difficulty);

    List<Question> findByTopic(String topic);

    @Query(value = "SELECT * FROM question ORDER BY embedding <-> ?1 LIMIT 5", nativeQuery = true)
    List<Question> findSimilarQuestions(float[] embedding);
}
