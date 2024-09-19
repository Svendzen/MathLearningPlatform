package org.svendzen.contentservice.services;

import org.springframework.stereotype.Service;
import org.svendzen.contentservice.models.MathProblem;

@Service
public class MathProblemService {
    public MathProblem generateAdditionProblem() {
        // generates and returns random addition math problem
        int num1 = (int) (Math.random() * 10);  // int 1-10
        int num2 = (int) (Math.random() * 10);
        String question = num1 + " + " + num2;
        int answer = num1 + num2;
        return new MathProblem(question, answer);
    }

}
