package org.svendzen.contentservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathProblemType;
import org.svendzen.contentservice.repositories.MathProblemRepository;

@Slf4j
@Service
public class MathProblemService {

    @Autowired
    private MathProblemRepository mathProblemRepository;

    public MathProblem generateAdditionProblem() {

        // generates and returns random addition math problem
        int num1 = (int) (Math.random() * 10) + 1;  // int 1-10
        int num2 = (int) (Math.random() * 10) + 1;
        String question = num1 + " + " + num2;
        int answer = num1 + num2;

        MathProblem mathProblem = new MathProblem();
        mathProblem.setQuestion(question);
        mathProblem.setAnswer(answer);
        mathProblem.setType(MathProblemType.ADDITION);

        log.info("Generated addition math problem: {} = {}", question, answer);

        // Saving MathProblem to DB and get ID assigned
        return mathProblemRepository.save(mathProblem);
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

        MathProblem mathProblem = new MathProblem();
        mathProblem.setQuestion(question);
        mathProblem.setAnswer(answer);
        mathProblem.setType(MathProblemType.SUBTRACTION);

        log.info("Generated subtraction math problem: {} = {}", question, answer);

        // saving
        return mathProblemRepository.save(mathProblem);
    }
}
