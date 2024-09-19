package org.svendzen.contentservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.services.MathProblemService;


@RestController
@RequestMapping("api/v1/")
public class MathProblemController {

    @Autowired
    private MathProblemService mathProblemService;

    @GetMapping("math/addition")
    public MathProblem getAdditionProblem() {
        return mathProblemService.generateAdditionProblem();
    }

}
