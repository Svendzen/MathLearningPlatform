package org.svendzen.gamificationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svendzen.enums.GameMode;
import org.svendzen.enums.MathTopic;
import org.svendzen.gamificationservice.models.StudentTrophy;
import org.svendzen.gamificationservice.services.StudentTrophyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/gamification")
public class StudentTrophyController {

    @Autowired
    private StudentTrophyService studentTrophyService;

    /**
     * GET: Retrieve all trophies for a specific student.
     * @param studentId - ID of the student
     * @return List of StudentTrophy objects
     */
    @GetMapping("/trophies/{studentId}")
    public ResponseEntity<List<StudentTrophy>> getTrophiesByStudentId(@PathVariable Long studentId) {
        List<StudentTrophy> trophies = studentTrophyService.getTrophiesByStudentId(studentId);
        return ResponseEntity.ok(trophies);
    }

    /**
     * GET: Retrieve a specific trophy for a student based on math topic and game mode.
     * @param studentId - ID of the student
     * @param mathTopic - Math topic
     * @param gameMode - Game mode
     * @return Optional StudentTrophy
     */
    @GetMapping("/trophies/{studentId}/specific")
    public ResponseEntity<StudentTrophy> getSpecificTrophy(
            @PathVariable Long studentId,
            @RequestParam String mathTopic,
            @RequestParam String gameMode) {
        Optional<StudentTrophy> trophy = studentTrophyService.getTrophyForExercise(
                studentId,
                MathTopic.valueOf(mathTopic.toUpperCase()),
                GameMode.valueOf(gameMode.toUpperCase())
        );

        return trophy.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST: Add or update a trophy for a student.
     * @param studentId - ID of the student
     * @param trophy - StudentTrophy object
     * @return Updated StudentTrophy object
     */
    @PostMapping("/trophies/{studentId}")
    public ResponseEntity<StudentTrophy> saveOrUpdateTrophy(
            @PathVariable Long studentId,
            @RequestBody StudentTrophy trophy) {
        StudentTrophy savedTrophy = studentTrophyService.saveOrUpdateTrophy(studentId, trophy);
        return ResponseEntity.ok(savedTrophy);
    }

    /**
     * DELETE: Remove a trophy by its ID.
     * @param trophyId - ID of the trophy to delete
     * @return Success message
     */
    @DeleteMapping("/trophies/{trophyId}")
    public ResponseEntity<String> deleteTrophy(@PathVariable Long trophyId) {
        studentTrophyService.deleteTrophy(trophyId);
        return ResponseEntity.ok("Trophy with ID " + trophyId + " has been deleted.");
    }
}
