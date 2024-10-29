package org.svendzen.progressservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/***
 * An ExerciseResult object captures the student's performance after completing an exercise.
 ***/

@Entity
@Data
public class ExerciseResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;  // The student who completed the exercise - found in UserService

    @Column(nullable = false)
    private String mathTopic;  // Topic like "Addition". Can be "Mixed" for custom exercise

    @Column(nullable = false)
    private String gameMode;   // Game mode like "Rapid Fire". "Custom" for custom exercises

    @Column(nullable = false)
    private int totalQuestions;  // Total questions in exercise

    @Column(nullable = false)
    private int correctAnswers;  // How many correct answers the student got

    @Column(nullable = false)
    private int score;  // Calculated as points or percentage-based

    @Column(nullable = false)
    private int scorePercentage;  // Calculated total score as percentage

    @Column(nullable = false)
    private long completionTime;  // Time taken to complete in seconds

    @Column(nullable = false)
    private LocalDateTime completionDate;  // Timestamp of completion
}
