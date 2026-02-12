package org.example.quizmaster.service;

import org.example.quizmaster.dto.QuestionDto;
import org.example.quizmaster.entity.Question;
import org.example.quizmaster.entity.QuizAttempt;
import org.example.quizmaster.dto.AnswerEvaluationDto;
import org.example.quizmaster.repository.QuestionRepository;
import org.example.quizmaster.repository.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final EmbeddingModel embeddingModel;
    private final AiEvaluationService aiEvaluationService;

    public Long startNewQuiz() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    @Cacheable(value = "questions", key = "#topic + '_' + #difficulty")
    public QuestionDto getNextQuestion(String topic, String difficulty) {
        if (difficulty == null) difficulty = "easy";

        Question question = questionRepository.findRandomByTopicAndDifficulty(topic, difficulty);
        if (question == null) {
            question = questionRepository.findByTopic(topic).stream().findAny().orElseThrow();
        }

        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setTopic(question.getTopic());
        dto.setDifficulty(question.getDifficulty());
        return dto;
    }

    public Question saveQuestion(Question question) {
        if (question.getEmbedding() == null) {
            float[] embedding = embeddingModel.embed(question.getText());
            question.setEmbedding(embedding);
        }
        return questionRepository.save(question);
    }

    public QuizAttempt recordAttempt(Long quizId, Long questionId, String userAnswer, Long userId) {
        Question question = questionRepository.findById(questionId).orElseThrow();
        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuizId(quizId);
        attempt.setQuestion(question);
        attempt.setUserAnswer(userAnswer);
        attempt.setUserId(userId);
        QuizAttempt saved = attemptRepository.save(attempt);

        // Async eval
        aiEvaluationService.evaluateAnswerAsync(question.getText(), userAnswer, question.getModelAnswerHint(), saved.getId());
        return saved;
    }

    // Update to use in controller
}
