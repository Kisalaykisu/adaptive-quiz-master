package com.example.quizmaster;

import org.example.quizmaster.dto.QuestionDto;
import org.example.quizmaster.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @Test
    void testGetNextQuestion() {
        QuestionDto dto = quizService.getNextQuestion("Geography", "easy");
        assert dto != null;
        // Add asserts
    }
}
