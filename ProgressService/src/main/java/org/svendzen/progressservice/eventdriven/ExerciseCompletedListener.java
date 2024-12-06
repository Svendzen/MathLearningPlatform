package org.svendzen.progressservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExerciseCompletedListener {

    @RabbitListener(queues = "exerciseCompletedQueue")
    public void handleExerciseCompletedEvent(ExerciseCompletedEvent event) {
        log.info("Received event: {}", event);

        log.warn("EVENT IS NOT HANDLED: ExerciseResult is NOT SAVED");
        log.warn("EVENT IS NOT HANDLED: StudentProgress is NOT SAVED");
    }
}
