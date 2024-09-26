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
    private int totalExercisesCompleted;

    @Column(nullable = false)
    private int totalCorrectAnswers;

    @Column(nullable = false)
    private int totalIncorrectAnswers;

    @Column(nullable = false)
    private double overallAccuracy;  // Correct / (Correct + Incorrect)

    // Game Mode Stats
    @Column(nullable = false)
    private int rapidFireExercisesCompleted;

    @Column(nullable = false)
    private int multipleChoiceExercisesCompleted;

    // Topic-Specific Stats
    @Column(nullable = false)
    private int additionExercisesCompleted;

    @Column(nullable = false)
    private int subtractionExercisesCompleted;

}
