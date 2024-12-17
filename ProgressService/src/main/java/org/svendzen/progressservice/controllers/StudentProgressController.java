package org.svendzen.progressservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svendzen.progressservice.models.StudentProgress;
import org.svendzen.progressservice.services.StudentProgressService;

@RestController
@RequestMapping("/api/v1/progress")
public class StudentProgressController {

    @Autowired
    private StudentProgressService studentProgressService;

    /**
     * GET: Retrieve progress for a student
     * @param studentId - ID of the student
     * @return StudentProgress object
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentProgress> getStudentProgress(@PathVariable Long studentId) {
        StudentProgress progress = studentProgressService.getOrCreateStudentProgress(studentId);
        return ResponseEntity.ok(progress);
    }

    /**
     * PUT: Update general statistics
     * @param studentId - ID of the student
     * @param correctAnswers - Number of correct answers
     * @param totalQuestions - Total number of questions
     * @return Updated StudentProgress object
     */
    @PutMapping("/{studentId}/stats")
    public ResponseEntity<StudentProgress> updateGeneralStats(
            @PathVariable Long studentId,
            @RequestParam int correctAnswers,
            @RequestParam int totalQuestions
    ) {
        StudentProgress progress = studentProgressService.getOrCreateStudentProgress(studentId);
        studentProgressService.updateGeneralStats(progress, correctAnswers, totalQuestions);
        studentProgressService.saveProgress(progress);
        return ResponseEntity.ok(progress);
    }

    /**
     * PUT: Update game mode statistics
     * @param studentId - ID of the student
     * @param gameMode - Name of the game mode
     * @return Updated StudentProgress object
     */
    @PutMapping("/{studentId}/gamemode")
    public ResponseEntity<StudentProgress> updateGameModeStats(
            @PathVariable Long studentId,
            @RequestParam String gameMode
    ) {
        StudentProgress progress = studentProgressService.getOrCreateStudentProgress(studentId);
        studentProgressService.updateGameModeStats(progress, gameMode);
        studentProgressService.saveProgress(progress);
        return ResponseEntity.ok(progress);
    }

    /**
     * PUT: Update topic-specific statistics
     * @param studentId - ID of the student
     * @param mathTopic - Name of the math topic
     * @return Updated StudentProgress object
     */
    @PutMapping("/{studentId}/topic")
    public ResponseEntity<StudentProgress> updateTopicStats(
            @PathVariable Long studentId,
            @RequestParam String mathTopic
    ) {
        StudentProgress progress = studentProgressService.getOrCreateStudentProgress(studentId);
        studentProgressService.updateTopicStats(progress, mathTopic);
        studentProgressService.saveProgress(progress);
        return ResponseEntity.ok(progress);
    }
}
