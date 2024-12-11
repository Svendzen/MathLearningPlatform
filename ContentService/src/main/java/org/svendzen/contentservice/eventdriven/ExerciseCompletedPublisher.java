package org.svendzen.contentservice.eventdriven;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExerciseCompletedPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ExerciseCompletedPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendExerciseCompletedEvent(ExerciseCompletedEvent event) {
        rabbitTemplate.convertAndSend("exerciseCompletedQueue", event);
    }
}
