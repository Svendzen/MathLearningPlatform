package org.svendzen.contentservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svendzen.contentservice.dtos.ProblemRequest;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathProblemType;
import org.svendzen.contentservice.services.MathProblemService;

@RestController
@RequestMapping("api/v1/mathproblem/")
public class MathProblemController {

    @Autowired
    private MathProblemService mathProblemService;

    @PostMapping("/generate-problem")
    public ResponseEntity<MathProblem> generateProblem(@RequestBody ProblemRequest request) {
        MathProblemType type = request.getType();
        MathProblem problem = mathProblemService.generateProblem(type);
        return ResponseEntity.ok(problem);
    }

}
