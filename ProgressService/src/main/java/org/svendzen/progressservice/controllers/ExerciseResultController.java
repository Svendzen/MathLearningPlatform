package org.svendzen.progressservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svendzen.events.ExerciseCompletedEvent;
import org.svendzen.progressservice.models.ExerciseResult;
import org.svendzen.progressservice.services.ExerciseResultService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise-results")
public class ExerciseResultController {

    @Autowired
    private ExerciseResultService exerciseResultService;

    /**
     * POST: Save an exercise result from an ExerciseCompletedEvent.
     * @param event - ExerciseCompletedEvent payload
     * @return Saved ExerciseResult
     */
    @PostMapping
    public ResponseEntity<ExerciseResult> saveExerciseResult(@RequestBody ExerciseCompletedEvent event) {
        ExerciseResult result = exerciseResultService.saveExerciseResult(event);
        return ResponseEntity.ok(result);
    }

    /**
     * POST: Save an already constructed ExerciseResult.
     * @param exerciseResult - ExerciseResult object
     * @return Saved ExerciseResult
     */
    @PostMapping("/manual")
    public ResponseEntity<ExerciseResult> saveExerciseResultManually(@RequestBody ExerciseResult exerciseResult) {
        ExerciseResult result = exerciseResultService.saveExerciseResult(exerciseResult);
        return ResponseEntity.ok(result);
    }

    /**
     * GET: Retrieve all exercise results for a specific student.
     * @param studentId - ID of the student
     * @return List of ExerciseResult objects
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<List<ExerciseResult>> getResultsByStudentId(@PathVariable Long studentId) {
        List<ExerciseResult> results = exerciseResultService.getResultsByStudentId(studentId);
        return ResponseEntity.ok(results);
    }

    /**
     * GET: Calculate the student's overall score based on their results.
     * @param studentId - ID of the student
     * @return Overall score as a double
     */
    @GetMapping("/{studentId}/overall-score")
    public ResponseEntity<Double> getOverallScore(@PathVariable Long studentId) {
        double overallScore = exerciseResultService.calculateOverallScore(studentId);
        return ResponseEntity.ok(overallScore);
    }
}
