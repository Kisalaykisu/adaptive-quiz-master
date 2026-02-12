package org.example.quizmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableRabbit
public class QuizMasterApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizMasterApplication.class, args);
    }
}