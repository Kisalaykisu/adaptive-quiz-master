package org.example.quizmaster.controller;

import org.example.quizmaster.dto.*;
import org.example.quizmaster.entity.User;
import org.example.quizmaster.service.AiEvaluationService;
import org.example.quizmaster.service.QuizService;
import org.example.quizmaster.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class QuizController {

    private final QuizService quizService;
    private final AiEvaluationService aiEvaluationService;
    private final RecommendationService recommendationService;

    @PostMapping("/start")
    public ResponseEntity<StartQuizResponse> startQuiz(@RequestParam String topic) {
        Long quizId = quizService.startNewQuiz();
        QuestionDto firstQuestion = quizService.getNextQuestion(topic, null);

        StartQuizResponse response = new StartQuizResponse();
        response.setQuizId(quizId);
        response.setFirstQuestion(firstQuestion);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/answer")
    public ResponseEntity<String> submitAnswer(@RequestBody SubmitAnswerRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        quizService.recordAttempt(request.getQuizId(), request.getQuestionId(), request.getUserAnswer(), user.getId());
        return ResponseEntity.ok("Answer submitted - feedback coming async!");
    }

    @GetMapping("/next-question")
    public ResponseEntity<QuestionDto> getNextQuestion(
            @RequestParam Long quizId,
            @RequestParam String topic,
            @RequestParam String suggestedDifficulty) {
        QuestionDto next = quizService.getNextQuestion(topic, suggestedDifficulty);
        return ResponseEntity.ok(next);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<RecommendationDto> getRecommendations() {
        RecommendationDto rec = recommendationService.getRecommendations();
        return ResponseEntity.ok(rec);
    }
}
