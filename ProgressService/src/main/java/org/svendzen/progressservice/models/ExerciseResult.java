package org.svendzen.progressservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/***
 * An ExerciseResult object captures the students performance after completing an exercise.
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
    private Long exerciseId;  // The exercise they completed - found in ContentService

    @Column(nullable = false)
    private int totalQuestions; // Total questions in exercise

    @Column(nullable = false)
    private int correctAnswers; // How many correct answers the student got

    @Column(nullable = false)
    private int incorrectAnswers;   // How many incorrect answers the student got

    @Column(nullable = false)
    private int score;  // Calculated as percentage or points

    @Column(nullable = false)
    private long completionTime;  // Time taken to complete

    @Column(nullable = false)
    private String mathTopic;   // Topic like "Addition". Can be "Mixed" for custom exercise

    @Column(nullable = false)   // Game mode like "Rapid Fire". "Custom" for custom exercises
    private String gameMode;

    @Column(nullable = false)
    private LocalDateTime completionDate;   // Timestamp of completion

}

