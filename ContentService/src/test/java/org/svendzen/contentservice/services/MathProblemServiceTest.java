package org.svendzen.contentservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svendzen.contentservice.models.MathProblem;

import static org.junit.jupiter.api.Assertions.*;


class MathProblemServiceTest {

    private MathProblemService mathProblemService;

    @BeforeEach
    void setUp() {
        mathProblemService = new MathProblemService();
    }

    @Test
    void testGenerateAdditionProblem() {
        // Call the method
        MathProblem problem = mathProblemService.generateAdditionProblem();

        // Verify that the question contains a "+" symbol
        assertTrue(problem.getQuestion().contains("+"), "The question should contain a plus sign");

        // Split the question and verify the answer
        String[] parts = problem.getQuestion().split(" \\+ ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);

        assertEquals(num1 + num2, problem.getAnswer(), "The answer should be the sum of the two numbers");
    }

    @Test
    void testGenerateSubtractionProblem() {
        // Call the method
        MathProblem problem = mathProblemService.generateSubtractionProblem();

        // Verify that the question contains a "-" symbol
        assertTrue(problem.getQuestion().contains("-"), "The question should contain a minus sign");

        // Split the question and verify the answer
        String[] parts = problem.getQuestion().split(" - ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);

        assertEquals(num1 - num2, problem.getAnswer(), "The answer should be the difference of the two numbers");
    }
}