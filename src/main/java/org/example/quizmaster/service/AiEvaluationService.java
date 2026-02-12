package org.example.quizmaster.service;


import org.example.quizmaster.dto.AnswerEvaluationDto;
import org.example.quizmaster.entity.QuizAttempt;
import org.example.quizmaster.message.EvaluationMessage;
import org.example.quizmaster.repository.QuizAttemptRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.quizmaster.config.RabbitMQConfig;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiEvaluationService {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final QuizAttemptRepository attemptRepository;

    public void evaluateAnswerAsync(String question, String userAnswer, String correctAnswerHint, Long attemptId) {
        EvaluationMessage msg = new EvaluationMessage(question, userAnswer, correctAnswerHint, attemptId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EVALUATION_QUEUE, msg);
    }

    @RabbitListener(queues = RabbitMQConfig.EVALUATION_QUEUE)
    public void processEvaluation(EvaluationMessage msg) {
        String promptText = """
                You are an expert teacher evaluating a student's open-ended answer.
                
                Question: %s
                
                Model correct answer (for reference only, do NOT copy it): %s
                
                Student answer: %s
                
                Return JSON only:
                {
                  "score": 0-10 (integer),
                  "feedback": "short, constructive feedback in 2-3 sentences",
                  "isCorrect": true/false,
                  "suggestedNextDifficulty": "easy|medium|hard"
                }
                """.formatted(msg.getQuestionText(), msg.getModelAnswerHint(), msg.getUserAnswer());

        String jsonResponse = chatModel.call(promptText);

        try {
            AnswerEvaluationDto eval = objectMapper.readValue(jsonResponse, AnswerEvaluationDto.class);
            // Update attempt
            QuizAttempt attempt = attemptRepository.findById(msg.getAttemptId()).orElseThrow();
            attempt.setScore(eval.getScore());
            attempt.setFeedback(eval.getFeedback());
            attemptRepository.save(attempt);
        } catch (Exception e) {
            // Log error, fallback
        }
    }
}
