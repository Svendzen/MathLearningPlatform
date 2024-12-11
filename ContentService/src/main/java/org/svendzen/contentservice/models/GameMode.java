package org.svendzen.contentservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * Represents a game mode with predefined rules and settings for math exercises.
 * A GameMode can support multiple MathTopics and has a priority for display ordering.
 */
@Entity
@Data
public class GameMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the GameMode

    @Column(nullable = false)
    private String name; // Name of the game mode (e.g., "Classic Mode", "Multiple Choice")

    @Column(nullable = false)
    private String description; // A brief description of the game mode's rules

    private int totalQuestions; // Default number of questions in this game mode

    private int maxPointsPerQuestion; // Maximum points achievable per question

    private int secondsPerQuestion; // Time limit per question (in seconds)

    private boolean isScoreBasedOnTime; // Determines if scoring is time-sensitive

    @ElementCollection(targetClass = MathTopic.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "game_mode_topics", joinColumns = @JoinColumn(name = "game_mode_id"))
    @Column(name = "math_topic")
    private List<MathTopic> supportedMathTopics; // List of MathTopics this GameMode supports

    @Column(nullable = false)
    private int priority; // Determines display order of game modes (lower number = higher priority)

    @Transient
    private List<MathProblem> problems; // Problems associated with this game mode (not persisted)
}
