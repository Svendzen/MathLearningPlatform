package org.svendzen.contentservice.models;

import jakarta.persistence.*;
import lombok.Data;

/***
 * An exercise object determines the structure of the math exercise before its
 * attempted by a student.
***/

@Entity
@Data
public class GameMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // The name of the game mode, e.g., "Rapid Fire" or "Multiple Choice"

    @Column(nullable = false)
    private String description; // A brief description of the game mode

    private int totalQuestions;  // Number of questions in the exercise (default)

    private int maxPointsPerQuestion;  // Maximum points achievable per question

    private int secondsPerQuestion; // Time limit for each question

    private boolean isScoreBasedOnTime; // Whether points are calculated based on time taken to answer correctly
}
