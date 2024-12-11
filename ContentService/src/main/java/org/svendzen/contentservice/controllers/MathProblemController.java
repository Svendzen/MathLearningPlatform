package org.svendzen.contentservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svendzen.contentservice.dtos.ProblemRequest;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathTopic;
import org.svendzen.contentservice.services.MathProblemService;

import java.util.List;

@RestController
@RequestMapping("api/v1/content/mathproblem/")
public class MathProblemController {

    @Autowired
    private MathProblemService mathProblemService;

    /**
     * Generate a math problem with a specific math topic
     */
    @GetMapping("/generate-problem/{topic}")
    public ResponseEntity<MathProblem> generateProblem(@PathVariable String topic) {
        try {
            MathTopic mathTopic = MathTopic.valueOf(topic.toUpperCase());
            MathProblem problem = mathProblemService.generateProblem(mathTopic);
            return ResponseEntity.ok(problem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle invalid topic
        }
    }

    /**
     * Fetch a random persistent problem for a given math topic.
     */
    @GetMapping("/random-persistent/{topic}")
    public ResponseEntity<MathProblem> getRandomPersistentProblem(@PathVariable MathTopic topic) {
        MathProblem problem = mathProblemService.getRandomPersistentProblem(topic);
        if (problem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(problem);
    }

    /**
     * Fetch a list of math problems for a given math topic and preferences.
     */
    @PostMapping("/get-problems")
    public ResponseEntity<List<MathProblem>> getProblems(@RequestBody ProblemRequest request) {
        MathTopic topic = request.getType();
        int count = request.getCount();
        boolean useDynamic = request.isUseDynamic();
        boolean usePersistent = request.isUsePersistent();

        List<MathProblem> problems = mathProblemService.getProblems(topic, count, useDynamic, usePersistent);
        return ResponseEntity.ok(problems);
    }

}
