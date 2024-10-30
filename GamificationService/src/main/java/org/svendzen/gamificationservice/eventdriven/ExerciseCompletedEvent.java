package org.svendzen.gamificationservice.eventdriven;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseCompletedEvent {
    private Long studentId;
    private String mathTopic;  // e.g., "ADDITION", from the MathTopic enum
    private String gameMode;   // e.g., "CLASSIC", based on the GameMode entity
    private int totalQuestions;
    private int correctAnswers;
    private int score;  // Total points scored in this exercise
    private int scorePercentage;  // Percentage of the correct answers
    private long completionTime;  // Time taken to complete the exercise in seconds
    private LocalDateTime completionDate;  // Date and time of exercise completion
}
