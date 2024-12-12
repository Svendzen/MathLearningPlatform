package org.svendzen.contentservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents a game mode with predefined rules and settings for math exercises.
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

    @Column(nullable = false)
    private int totalQuestions; // Total number of questions in this game mode.

    @Column(nullable = false)
    private int maxPointsPerQuestion; // Maximum points per question, default is 1000.

    @Column(nullable = false)
    private int millisecondsPerQuestion; // Time per question in milliseconds, default is 10000.

    @Column(nullable = false)
    private boolean isScoreBasedOnTime; // Indicates if scoring depends on time taken.

    @ElementCollection(targetClass = MathTopic.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "game_mode_supported_topics", joinColumns = @JoinColumn(name = "game_mode_id"))
    @Column(name = "math_topic")
    private List<MathTopic> supportedMathTopics; // Supported math topics for this game mode.

    @Column(nullable = false)
    private int priority; // Determines display order; lower values are higher priority.

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
        if (totalQuestions <= 0) {
            throw new IllegalArgumentException("totalQuestions must be greater than 0.");
        }

        // Validate maxPointsPerQuestion
        if (maxPointsPerQuestion <= 0) {
            throw new IllegalArgumentException("maxPointsPerQuestion must be greater than 0.");
        }

        // Validate millisecondsPerQuestion
        if (millisecondsPerQuestion < 0) {
            throw new IllegalArgumentException("millisecondsPerQuestion cannot be negative.");
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
