package org.svendzen.contentservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents a game mode with predefined rules and settings for math exercises.
 * A GameMode can support multiple MathTopics and has a priority for display ordering.
 *
 * Key Field Details:
 * - `name`: Name of the game mode (e.g., "Classic Mode").
 * - `description`: A brief description of the game mode's rules.
 * - `totalQuestions`: Default number of questions in this game mode.
 * - `maxPointsPerQuestion`: Maximum points achievable per question.
 * - `secondsPerQuestion`: Time limit per question in seconds.
 *      - `0` indicates no time limit.
 * - `isScoreBasedOnTime`: Indicates whether scoring is based on the time taken to answer.
 * - `supportedMathTopics`: List of MathTopics this GameMode supports.
 * - `priority`: Determines the display order of game modes (lower number = higher priority).
 */
@Entity
@Data
public class GameMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Name of the game mode, must not be null or empty.

    @Column(nullable = false)
    private String description; // Description of the game mode, must not be null or empty.

    private int totalQuestions; // Must be non-negative.

    private int maxPointsPerQuestion; // Must be non-negative.

    @Column(nullable = false, columnDefinition = "int default 0")
    private int secondsPerQuestion; // Default to 0 for no time limit; must be non-negative.

    private boolean isScoreBasedOnTime; // Indicates if scoring is time-sensitive.

    @ElementCollection(targetClass = MathTopic.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "game_mode_supported_topics", joinColumns = @JoinColumn(name = "game_mode_id"))
    @Column(name = "math_topic")
    private List<MathTopic> supportedMathTopics; // Must not be empty for a valid GameMode.

    @Column(nullable = false)
    private int priority; // Determines display order; must be positive.

    @Transient
    private List<MathProblem> problems; // Transient field; not persisted in the database.

    /**
     * Validates and ensures defaults for GameMode fields before persisting or updating.
     */
    @PrePersist
    @PreUpdate
    private void validateDefaults() {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("GameMode name cannot be null or empty.");
        }

        // Validate description
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("GameMode description cannot be null or empty.");
        }

        // Validate totalQuestions
        if (totalQuestions < 0) {
            throw new IllegalArgumentException("totalQuestions must be non-negative.");
        }

        // Validate maxPointsPerQuestion
        if (maxPointsPerQuestion < 0) {
            throw new IllegalArgumentException("maxPointsPerQuestion must be non-negative.");
        }

        // Validate secondsPerQuestion
        if (secondsPerQuestion < 0) {
            throw new IllegalArgumentException("secondsPerQuestion cannot be negative.");
        }

        // Validate priority
        if (priority <= 0) {
            throw new IllegalArgumentException("priority must be a positive value.");
        }

        // Validate supportedMathTopics
        if (supportedMathTopics == null || supportedMathTopics.isEmpty()) {
            throw new IllegalArgumentException("supportedMathTopics must contain at least one MathTopic.");
        }
    }
}
