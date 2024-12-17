package org.svendzen.progressservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.events.ExerciseCompletedEvent;
import org.svendzen.progressservice.models.StudentProgress;
import org.svendzen.progressservice.services.ExerciseResultService;
import org.svendzen.progressservice.services.StudentProgressService;

@Slf4j
@Service
public class ExerciseCompletedListener {

    @Autowired
    ExerciseResultService exerciseResultService;

    @Autowired
    StudentProgressService studentProgressService;

    @RabbitListener(queues = "exerciseCompletedQueue")
    public void handleExerciseCompletedEvent(ExerciseCompletedEvent event) {
        log.info("Received event: {}", event);

        // Save exercise result
        exerciseResultService.saveExerciseResult(event);

        // Crete or update progress
        StudentProgress progress = studentProgressService.getOrCreateStudentProgress(event.getStudentId());

        // Update stats
        studentProgressService.updateGeneralStats(progress, event.getCorrectAnswers(), event.getTotalQuestions());
        studentProgressService.updateGameModeStats(progress, event.getGameMode());
        studentProgressService.updateTopicStats(progress, event.getMathTopic());

        // Save progress
        studentProgressService.saveProgress(progress);
    }
}
