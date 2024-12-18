package org.svendzen.gamificationservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.enums.GameMode;
import org.svendzen.enums.MathTopic;
import org.svendzen.events.ExerciseCompletedEvent;
import org.svendzen.gamificationservice.services.StudentTrophyService;

@Slf4j
@Service
public class ExerciseCompletedListener {

    @Autowired
    private StudentTrophyService studentTrophyService;

    @RabbitListener(queues = "exerciseCompletedQueue")
    public void handleExerciseCompletedEvent(ExerciseCompletedEvent event) {
        log.info("Received ExerciseCompletedEvent: {}", event);

        try {
            // Convert to Enum-safe values
            MathTopic mathTopic = MathTopic.valueOf(event.getMathTopic().toUpperCase());
            GameMode gameMode = GameMode.valueOf(event.getGameMode().toUpperCase().replace(" ", "_"));

            // Delegate to the service to handle business logic
            studentTrophyService.handleExerciseResult(
                    event.getScorePercentage(),
                    event.getStudentId(),
                    mathTopic,
                    gameMode
            );

            log.info("Successfully processed ExerciseCompletedEvent for student: {}", event.getStudentId());

        } catch (IllegalArgumentException e) {
            log.error("Invalid mathTopic or gameMode in the event: {}", event, e);
        } catch (Exception e) {
            log.error("Failed to process ExerciseCompletedEvent: {}", event, e);
        }
    }
}
