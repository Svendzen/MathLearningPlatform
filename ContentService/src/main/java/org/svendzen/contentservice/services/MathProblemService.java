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

        log.info("Generated Math Problem: {} = {}", question, answer);

        return new MathProblem(question, answer);
    }

}
