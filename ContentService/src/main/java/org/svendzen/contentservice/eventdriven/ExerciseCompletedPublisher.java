package org.svendzen.contentservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExerciseCompletedPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;

    public ExerciseCompletedPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${amqp.exchange.name}") String exchangeName
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    public void sendExerciseCompletedEvent(ExerciseCompletedEvent event) {
        try {
            String routingKey = "exercise.completed";
            log.info("Publishing event to exchange '{}', routing key '{}': {}", exchangeName, routingKey, event);
            rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
        } catch (Exception e) {
            log.error("Failed to publish event to RabbitMQ", e);
        }
    }
}
