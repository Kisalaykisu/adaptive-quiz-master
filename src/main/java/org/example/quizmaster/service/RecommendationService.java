package org.example.quizmaster.service;


import org.example.quizmaster.dto.RecommendationDto;
import org.example.quizmaster.entity.QuizAttempt;
import org.example.quizmaster.repository.QuestionRepository;
import org.example.quizmaster.repository.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.example.quizmaster.entity.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final QuizAttemptRepository attemptRepository;
    private final QuestionRepository questionRepository;
    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;

    public RecommendationDto getRecommendations() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Assume getUserId from username (add method in UserService)
        Long userId = 1L; // Placeholder - replace with real

        List<QuizAttempt> attempts = attemptRepository.findByUserId(userId);

        String summary = attempts.stream()
                .map(a -> "Topic: " + a.getQuestion().getTopic() + ", Score: " + a.getScore())
                .reduce("", (a, b) -> a + "\n" + b);

        String prompt = "Analyze performance: " + summary + "\nRecommend weak topics and next steps in 3 sentences.";

        String recText = chatModel.call(prompt);

        // Semantic: Embed a weak topic summary, find similar
        String weakSummary = "weak areas from analysis"; // Derive from recText
        float[] weakEmbedding = embeddingModel.embed(weakSummary);
        List<Question> similar = questionRepository.findSimilarQuestions(weakEmbedding);

        RecommendationDto dto = new RecommendationDto();
        dto.setRecommendations(recText);
        dto.setSuggestedQuestions(similar.stream().map(Question::getText).toList());
        return dto;
    }
}
