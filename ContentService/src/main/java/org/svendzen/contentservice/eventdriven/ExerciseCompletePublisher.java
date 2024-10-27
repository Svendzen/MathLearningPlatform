package org.svendzen.contentservice.eventdriven;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseCompletePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;      // This is for JSON conversion

    public void sendExerciseCompletionMessage(ExerciseCompletedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend("progressQueue", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
