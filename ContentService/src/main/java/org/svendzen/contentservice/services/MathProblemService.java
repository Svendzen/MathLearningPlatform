package org.svendzen.contentservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.contentservice.models.DynamicMathProblem;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathTopic;
import org.svendzen.contentservice.models.PersistentMathProblem;
import org.svendzen.contentservice.repositories.PersistentMathProblemRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Service layer for handling business logic related to MathProblems.
 */
@Slf4j
@Service
public class MathProblemService {


    private final PersistentMathProblemRepository persistentMathProblemRepository;

    public MathProblemService(PersistentMathProblemRepository persistentMathProblemRepository) {
        this.persistentMathProblemRepository = persistentMathProblemRepository;
    }

    /**
     * Saves a PersistentMathProblem to the database.
     *
     * @param problem The problem to be saved.
     * @return The saved PersistentMathProblem.
     */
    public PersistentMathProblem saveMathProblem(PersistentMathProblem problem) {
        return persistentMathProblemRepository.save(problem);
    }

    /**
     * Retrieves a single random persistent math problem for a given topic.
     *
     * @param topic The math topic (e.g., ADDITION, SUBTRACTION).
     * @return A random PersistentMathProblem or null if no problems exist for the topic.
     */
    public PersistentMathProblem getRandomPersistentProblem(MathTopic topic) {
        List<PersistentMathProblem> problems = getPredefinedProblemsByType(topic);
        if (problems.isEmpty()) {
            log.warn("No persistent problems found for topic: {}", topic);
            return null;
        }
        return problems.get(new Random().nextInt(problems.size()));
    }

    /**
     * Generates a single dynamic math problem for a given MathTopic.
     *
     * @param topic The MathTopic (e.g., ADDITION, SUBTRACTION) to generate a problem for.
     * @return A dynamically generated DynamicMathProblem.
     */
    public DynamicMathProblem generateProblem(MathTopic topic) {
        DynamicMathProblem mathProblem = new DynamicMathProblem();
        int num1 = (int) (Math.random() * 10) + 1;
        int num2 = (int) (Math.random() * 10) + 1;

        switch (topic) {
            case ADDITION:
                mathProblem.setQuestion(num1 + " + " + num2);
                mathProblem.setAnswer(num1 + num2);
                break;
            case SUBTRACTION:
                mathProblem.setQuestion(Math.max(num1, num2) + " - " + Math.min(num1, num2));
                mathProblem.setAnswer(Math.abs(num1 - num2));
                break;
            case MULTIPLICATION:
                mathProblem.setQuestion(num1 + " * " + num2);
                mathProblem.setAnswer(num1 * num2);
                break;
            case DIVISION:
                // Generate a divisor and answer, then calculate the dividend
                int divisor = (int) (Math.random() * 9) + 1;  // Ensure divisor is between 1 and 10
                int answer = (int) (Math.random() * 10) + 1;  // Answer between 1 and 10
                int dividend = divisor * answer;  // Makes sure division is exact

                mathProblem.setQuestion(dividend + " / " + divisor);
                mathProblem.setAnswer(answer);
                break;
        }

        mathProblem.setType(topic);
        log.info("Generated {} problem: {} = {}", topic, mathProblem.getQuestion(), mathProblem.getAnswer());
        return mathProblem;
    }

    /**
     * Retrieves a list of predefined PersistentMathProblems for a given MathTopic.
     *
     * @param topic The MathTopic to filter predefined problems by.
     * @return A list of PersistentMathProblems for the given topic.
     */
    public List<PersistentMathProblem> getPredefinedProblemsByType(MathTopic topic) {
        return persistentMathProblemRepository.findByType(topic);
    }

    /**
     * Retrieves a list of math problems for a given topic.
     * Supports dynamic, persistent, or a combination of both.
     *
     * @param topic The math topic (e.g., ADDITION, SUBTRACTION).
     * @param problemCount Number of problems to retrieve.
     * @param useDynamicProblems If true, generate dynamic problems.
     * @param usePersistentProblems If true, fetch persistent problems.
     * @return A list of MathProblems based on the specified options.
     */
    public List<MathProblem> getProblems(
            MathTopic topic,
            int problemCount,
            boolean useDynamicProblems,
            boolean usePersistentProblems) {

        List<MathProblem> problems = new ArrayList<>();

        // Fetch persistent problems if enabled
        if (usePersistentProblems) {
            for (int i = 0; i < problemCount; i++) {
                PersistentMathProblem problem = getRandomPersistentProblem(topic);
                if (problem != null) {
                    problems.add(problem);
                }
            }
        }

        // Generate dynamic problems if enabled
        if (useDynamicProblems) {
            for (int i = 0; i < problemCount; i++) {
                problems.add(generateProblem(topic));
            }
        }

        log.info("Retrieved {} total problems ({} persistent, {} dynamic) for topic {}.",
                problems.size(),
                usePersistentProblems ? problems.stream().filter(p -> p instanceof PersistentMathProblem).count() : 0,
                useDynamicProblems ? problemCount : 0,
                topic);

        return problems;
    }

}
