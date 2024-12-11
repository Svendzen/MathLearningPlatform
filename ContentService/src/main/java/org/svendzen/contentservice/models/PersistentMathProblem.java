package org.svendzen.contentservice.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a math problem stored persistently in the database.
 * Used for predefined math problems.
 */
@Entity
@Data
public class PersistentMathProblem implements MathProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the persistent problem

    @Column(nullable = false)
    private String question; // The text of the problem (e.g., "5 + 3")

    @Column(nullable = false)
    private int answer; // The correct solution to the problem (e.g., 8)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MathTopic type; // The type of math problem (e.g., ADDITION, MULTIPLICATION)
}

