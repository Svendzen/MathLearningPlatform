package org.svendzen.contentservice.models;

/**
 * Represents the base structure of a math problem.
 * Implemented by both PersistentMathProblem (stored in DB) and DynamicMathProblem (runtime-generated).
 */
public interface MathProblem {
    String getQuestion(); // The text of the math problem
    int getAnswer();      // The solution to the problem
    MathTopic getType(); // The type of the problem (e.g., ADDITION, SUBTRACTION)
}
