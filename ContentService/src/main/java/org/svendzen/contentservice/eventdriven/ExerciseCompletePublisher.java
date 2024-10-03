package org.svendzen.contentservice.eventdriven;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseCompletePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendExerciseCompletionMessage(String message) {
        // message could be JSON - this is temporary
        rabbitTemplate.convertAndSend("progressQueue", message);
    }

}
