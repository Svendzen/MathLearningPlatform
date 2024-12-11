package org.svendzen.contentservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svendzen.contentservice.eventdriven.ExerciseCompletedEvent;
import org.svendzen.contentservice.models.GameMode;
import org.svendzen.contentservice.models.MathTopic;
import org.svendzen.contentservice.services.GameModeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/content/gamemode/")
public class GameModeController {

    @Autowired
    private GameModeService gameModeService;

    /**
     * Fetch all available game modes.
     *
     * @return List of all game modes.
     */
    @GetMapping("/all")
    public ResponseEntity<List<GameMode>> getAllGameModes() {
        List<GameMode> gameModes = gameModeService.getAllGameModes();
        return ResponseEntity.ok(gameModes);
    }

    /**
     * Initialize a game mode by topic and number of problems.
     */
    @PostMapping("/initialize")
    public ResponseEntity<GameMode> initializeGameMode(
            @RequestParam Long gameModeId,
            @RequestParam MathTopic topic,
            @RequestParam int problemCount) {

        GameMode initializedGameMode = gameModeService.initializeGameMode(gameModeId, topic, problemCount);
        return ResponseEntity.ok(initializedGameMode);
    }

    /**
     * Get a specific game mode by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GameMode> getGameModeById(@PathVariable Long id) {
        GameMode gameMode = gameModeService.getGameModeById(id);
        if (gameMode == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameMode);
    }

    /**
     * Fetch game modes by a specific MathTopic.
     *
     * @param topic The MathTopic to filter by.
     * @return List of game modes supporting the given MathTopic.
     */
    @GetMapping("/by-topic/{topic}")
    public ResponseEntity<List<GameMode>> getGameModesByTopic(@PathVariable String topic) {
        try {
            MathTopic mathTopic = MathTopic.valueOf(topic.toUpperCase()); // Ensure case insensitivity
            List<GameMode> gameModes = gameModeService.getGameModesByTopic(mathTopic);
            return ResponseEntity.ok(gameModes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Invalid MathTopic
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeExercise(@RequestBody ExerciseCompletedEvent exerciseEvent) {
        gameModeService.completeExercise(exerciseEvent);
        return ResponseEntity.ok().build();
    }
}
