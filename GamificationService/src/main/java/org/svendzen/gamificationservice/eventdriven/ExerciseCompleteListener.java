package org.svendzen.gamificationservice.eventdriven;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.gamificationservice.services.StudentTrophyService;
import org.svendzen.events.ExerciseCompletedEvent;

@Slf4j
@Service
public class ExerciseCompleteListener {

    @Autowired
    private StudentTrophyService studentTrophyService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "exerciseCompletedQueue")
    public void receiveMessage(String message) {
        try {
            // Deserialize the JSON message into ExerciseCompletedEvent object
            ExerciseCompletedEvent event = objectMapper.readValue(message, ExerciseCompletedEvent.class);
            log.info("Received ExerciseCompletedEvent: {}", event);

            // Handle the event by passing it to StudentTrophyService
            studentTrophyService.handleExerciseResult(
                    event.getScorePercentage(),
                    event.getStudentId(),
                    event.getMathTopic(),
                    event.getGameMode()
            );
        } catch (JsonProcessingException e) {
            log.error("Failed to process ExerciseCompletedEvent message: {}", message, e);
        }
    }
}
