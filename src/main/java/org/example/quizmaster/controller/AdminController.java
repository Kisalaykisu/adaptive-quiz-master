package org.example.quizmaster.controller;

import org.example.quizmaster.dto.GenerateQuestionRequest;
import org.example.quizmaster.entity.Question;
import org.example.quizmaster.service.AiQuestionGeneratorService;
import org.example.quizmaster.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class AdminController {

    private final AiQuestionGeneratorService generatorService;
    private final QuizService quizService;

    @PostMapping("/generate-question")
    public ResponseEntity<Question> generateQuestion(@RequestBody GenerateQuestionRequest request) {
        Question question = generatorService.generateQuestion(request);
        Question saved = quizService.saveQuestion(question);
        return ResponseEntity.ok(saved);
    }
}