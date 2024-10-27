package org.svendzen.gamificationservice.eventdriven;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.gamificationservice.services.StudentTrophyService;

@Service
public class ExerciseCompleteListener {

    @Autowired
    private StudentTrophyService studentTrophyService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "progressQueue")
    public void receiveMessage(String message) {
        try {// Deserialize the JSON message
            ExerciseCompletedEvent event = objectMapper.readValue(message, ExerciseCompletedEvent.class);

            // Handle the event by passing it to StudentTrophyService
            studentTrophyService.handleExerciseResult(
                    event.getScorePercentage(),
                    event.getStudentId(),
                    event.getMathTopic(),
                    event.getGameMode()
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Log this better
        }
    }
}
