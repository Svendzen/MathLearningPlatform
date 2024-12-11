package org.svendzen.contentservice.eventdriven;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue exerciseCompletedQueue() {
        // Setting durable to true ensures the queue persists even after RabbitMQ restarts
        return new Queue("exerciseCompletedQueue", true);
    }
}
