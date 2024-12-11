package org.svendzen.contentservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.contentservice.eventdriven.ExerciseCompletedEvent;
import org.svendzen.contentservice.eventdriven.ExerciseCompletedPublisher;
import org.svendzen.contentservice.models.GameMode;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathTopic;
import org.svendzen.contentservice.repositories.GameModeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing GameModes and their associated math problems.
 */
@Service
@Slf4j
public class GameModeService {

    private final GameModeRepository gameModeRepository;
    private final MathProblemService mathProblemService;
    private final ExerciseCompletedPublisher publisher;

    @Autowired
    public GameModeService(GameModeRepository gameModeRepository, MathProblemService mathProblemService, ExerciseCompletedPublisher publisher) {
        this.gameModeRepository = gameModeRepository;
        this.mathProblemService = mathProblemService;
        this.publisher = publisher;
    }

    /**
     * Retrieve all game modes.
     *
     * @return List of all game modes in the database.
     */
    public List<GameMode> getAllGameModes() {
        return gameModeRepository.findAll();
    }

    /**
     * Retrieve a specific game mode by its ID.
     *
     * @param id The ID of the game mode.
     * @return The GameMode if found, otherwise null.
     */
    public GameMode getGameModeById(Long id) {
        Optional<GameMode> optionalGameMode = gameModeRepository.findById(id);
        return optionalGameMode.orElse(null);
    }

    /**
     * Initialize a game mode with math problems for a specific topic.
     *
     * @param gameModeId  The ID of the game mode to initialize.
     * @param topic       The MathTopic for the problems.
     * @param problemCount The number of problems to include.
     * @return The initialized GameMode, or null if the game mode is not found.
     */
    public GameMode initializeGameMode(Long gameModeId, MathTopic topic, int problemCount) {
        GameMode gameMode = getGameModeById(gameModeId);
        if (gameMode == null) {
            return null; // GameMode not found
        }

        // Generate or fetch math problems
        List<MathProblem> problems = mathProblemService.getProblems(topic, problemCount, true, true);
        gameMode.setProblems(problems);

        return gameMode;
    }

    /**
     * Retrieves game modes supporting a given MathTopic, ordered by priority.
     *
     * @param topic The MathTopic to filter game modes by.
     * @return A list of ordered game modes supporting the given topic.
     */
    public List<GameMode> getGameModesByTopic(MathTopic topic) {
        return gameModeRepository.findBySupportedMathTopicsContainingOrderByPriorityAsc(topic);
    }

    public void completeExercise(ExerciseCompletedEvent exerciseEvent) {
        // Validate event data (optional)
        if (exerciseEvent == null || exerciseEvent.getStudentId() == null || exerciseEvent.getMathTopic() == null) {
            throw new IllegalArgumentException("Invalid ExerciseCompletedEvent data.");
        }

        // Log the completion
        log.info("Processing exercise completion: {}", exerciseEvent);

        // Publish the event to RabbitMQ
        publisher.sendExerciseCompletedEvent(exerciseEvent);
    }
}


