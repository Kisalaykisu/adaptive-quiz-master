package org.example.quizmaster.service;

import org.example.quizmaster.dto.GenerateQuestionRequest;
import org.example.quizmaster.entity.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiQuestionGeneratorService {

    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private final ObjectMapper objectMapper;

    public Question generateQuestion(GenerateQuestionRequest request) {
        String prompt = """
                Generate an open-ended question for topic: %s, difficulty: %s.
                Provide model answer hint.
                Return JSON: { "text": "...", "modelAnswerHint": "..." }
                """.formatted(request.getTopic(), request.getDifficulty());

        String json = chatModel.call(prompt);

        try {
            // Parse JSON
            @SuppressWarnings("unchecked")
            Map<String, String> map = objectMapper.readValue(json, Map.class);
            Question question = new Question();
            question.setText(map.get("text"));
            question.setModelAnswerHint(map.get("modelAnswerHint"));
            question.setTopic(request.getTopic());
            question.setDifficulty(request.getDifficulty());

            // Generate embedding
            float[] embedding = embeddingModel.embed(question.getText());
            question.setEmbedding(embedding);

            return question;
        } catch (Exception e) {
            // Fallback
            Question fallback = new Question();
            fallback.setText("Fallback question");
            // ...
            return fallback;
        }
    }
}
