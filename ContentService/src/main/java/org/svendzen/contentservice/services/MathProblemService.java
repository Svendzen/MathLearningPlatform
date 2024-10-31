package org.svendzen.contentservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathProblemType;
import org.svendzen.contentservice.models.PersistentMathProblem;
import org.svendzen.contentservice.repositories.MathProblemRepository;

import java.util.List;

@Slf4j
@Service
public class MathProblemService {

    @Autowired
    private MathProblemRepository mathProblemRepository;

    public PersistentMathProblem saveMathProblem(PersistentMathProblem problem) {
        return mathProblemRepository.save(problem);
    }

    public MathProblem generateProblem(MathProblemType type) {
        MathProblem mathProblem = new MathProblem();
        int num1 = (int) (Math.random() * 10) + 1;
        int num2 = (int) (Math.random() * 10) + 1;

        switch (type) {
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

        mathProblem.setType(type);
        log.info("Generated {} problem: {} = {}", type, mathProblem.getQuestion(), mathProblem.getAnswer());
        return mathProblem;
    }

    public List<PersistentMathProblem> getPredefinedProblemsByType(MathProblemType type) {
        return mathProblemRepository.findByType(type);
    }


}
