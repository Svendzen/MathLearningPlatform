package org.svendzen.contentservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.contentservice.eventdriven.ExerciseCompletePublisher;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseCompletePublisher publisher;

    public void completeExercise(String studentId, String exerciseId) {
        // Business logic for completing an exercise goes here

        // Send message to RabbitMQ
        String message = "Student " + studentId + " completed exercise " + exerciseId;
        publisher.sendExerciseCompletionMessage(message);
    }
}
