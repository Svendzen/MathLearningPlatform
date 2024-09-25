package org.svendzen.contentservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.svendzen.contentservice.models.MathProblem;

@Slf4j
@Service
public class MathProblemService {
    public MathProblem generateAdditionProblem() {

        // generates and returns random addition math problem
        int num1 = (int) (Math.random() * 10) + 1;  // int 1-10
        int num2 = (int) (Math.random() * 10) + 1;
        String question = num1 + " + " + num2;
        int answer = num1 + num2;

        log.info("Generated addition math problem: {} = {}", question, answer);

        return new MathProblem(question, answer);
    }

    public MathProblem generateSubtractionProblem() {

        int num1 = (int) (Math.random() * 10) + 1;
        int num2 = (int) (Math.random() * 10) + 1;

        String question;
        int answer;

        if (num1 < num2) {
            question = num2 + " - " + num1;
            answer = num2 - num1;
        } else {
            question = num1 + " - " + num2;
            answer = num1 - num2;
        }

        log.info("Generated subtraction math problem: {} = {}", question, answer);

        return new MathProblem(question, answer);
    }
}
