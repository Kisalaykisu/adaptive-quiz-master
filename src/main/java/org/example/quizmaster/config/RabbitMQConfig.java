package org.example.quizmaster.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EVALUATION_QUEUE = "evaluationQueue";

    @Bean
    public Queue evaluationQueue() {
        return new Queue(EVALUATION_QUEUE, false);
    }
}
