package org.svendzen.contentservice.models;

import lombok.Data;

/**
 * Represents a dynamically generated math problem.
 * These problems are created at runtime and not stored in the database.
 */
@Data
public class DynamicMathProblem implements MathProblem {
    private Long id;            // Temporary identifier for runtime use (frontend expects IDs for all MathProblems
    private String question;    // The text of the problem (e.g., "7 - 4")
    private int answer;         // The correct solution to the problem (e.g., 3)
    private MathTopic type;     // The type of math problem (e.g., SUBTRACTION)
}

