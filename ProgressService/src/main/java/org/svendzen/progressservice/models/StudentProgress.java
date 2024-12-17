package org.svendzen.progressservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StudentProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;  // Links progress to the student - from UserService

    // General Stats
    @Column(nullable = false)
    private long totalExercisesCompleted;

    @Column(nullable = false)
    private long totalCorrectAnswers;

    @Column(nullable = false)
    private long totalIncorrectAnswers;

    // Dynamic calculation can be performed in the code instead of storing it
    @Transient  // Not stored in the database
    public double getOverallAccuracy() {
        long totalAttempts = totalCorrectAnswers + totalIncorrectAnswers;
        return totalAttempts == 0 ? 0.0 : ((double) totalCorrectAnswers / totalAttempts);
    }

    // Game Mode Stats (e.g., Classic Mode, Multiple Choice)
    @Column(nullable = false)
    private long classicExercisesCompleted;

    @Column(nullable = false)
    private long multipleChoiceExercisesCompleted;

    // Topic-Specific Stats (e.g., Addition, Subtraction)
    @Column(nullable = false)
    private long additionExercisesCompleted;

    @Column(nullable = false)
    private long subtractionExercisesCompleted;

    @Column(nullable = false)
    private long multiplicationExercisesCompleted;

    @Column(nullable = false)
    private long divisionExercisesCompleted;
}
