package org.svendzen.contentservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathTopic;
import org.svendzen.contentservice.repositories.PersistentMathProblemRepository;

import static org.junit.jupiter.api.Assertions.*;

class MathProblemServiceTest {

    private MathProblemService mathProblemService;

    PersistentMathProblemRepository persistentMathProblemRepository;

    @BeforeEach
    void setUp() {
        mathProblemService = new MathProblemService(persistentMathProblemRepository);
    }

    @Test
    void testGenerateAdditionProblem() {
        // Call the method
        MathProblem problem = mathProblemService.generateProblem(MathTopic.ADDITION);

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
        MathProblem problem = mathProblemService.generateProblem(MathTopic.SUBTRACTION);

        // Verify that the question contains a "-" symbol
        assertTrue(problem.getQuestion().contains("-"), "The question should contain a minus sign");

        // Split the question and verify the answer
        String[] parts = problem.getQuestion().split(" - ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);

        assertEquals(Math.abs(num1 - num2), problem.getAnswer(), "The answer should be the difference of the two numbers");
    }

    @Test
    void testGenerateMultiplicationProblem() {
        // Call the method
        MathProblem problem = mathProblemService.generateProblem(MathTopic.MULTIPLICATION);

        // Verify that the question contains a "*" symbol
        assertTrue(problem.getQuestion().contains("*"), "The question should contain a multiplication sign");

        // Split the question and verify the answer
        String[] parts = problem.getQuestion().split(" \\* ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);

        assertEquals(num1 * num2, problem.getAnswer(), "The answer should be the product of the two numbers");
    }

    @Test
    void testGenerateDivisionProblem() {
        // Call the method
        MathProblem problem = mathProblemService.generateProblem(MathTopic.DIVISION);

        // Verify that the question contains a "/" symbol
        assertTrue(problem.getQuestion().contains("/"), "The question should contain a division sign");

        // Split the question and verify the answer
        String[] parts = problem.getQuestion().split(" / ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);

        assertEquals(num1 / num2, problem.getAnswer(), "The answer should be the quotient of the two numbers");
        assertTrue(num1 % num2 == 0, "The result should be an integer with no remainder");
    }
}
