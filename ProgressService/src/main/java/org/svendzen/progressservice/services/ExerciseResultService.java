package org.svendzen.progressservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.events.ExerciseCompletedEvent;
import org.svendzen.progressservice.models.ExerciseResult;
import org.svendzen.progressservice.repositories.ExerciseResultRepository;

import java.util.List;

@Service
@Slf4j
public class ExerciseResultService {

    @Autowired
    private ExerciseResultRepository exerciseResultRepository;

    // Save a new exercise result from an ExerciseCompletedEvent
    public ExerciseResult saveExerciseResult(ExerciseCompletedEvent event) {
        ExerciseResult result = new ExerciseResult();
        result.setStudentId(event.getStudentId());
        result.setMathTopic(event.getMathTopic());
        result.setGameMode(event.getGameMode());
        result.setTotalQuestions(event.getTotalQuestions());
        result.setCorrectAnswers(event.getCorrectAnswers());
        result.setScore(event.getScore());
        result.setScorePercentage(event.getScorePercentage());
        result.setCompletionTime(event.getCompletionTime());
        result.setCompletionDate(event.getCompletionDate());

        log.info("Saved ExerciseResult: {}", result);
        return exerciseResultRepository.save(result);
    }

    // Existing method: Save an already constructed ExerciseResult
    public ExerciseResult saveExerciseResult(ExerciseResult exerciseResult) {
        log.info("Saved ExerciseResult: {}", exerciseResult);
        return exerciseResultRepository.save(exerciseResult);
    }

    // Get all exercise results for a specific student
    public List<ExerciseResult> getResultsByStudentId(Long studentId) {
        return exerciseResultRepository.findByStudentId(studentId);
    }

    // Calculate the student's overall score based on their results
    public double calculateOverallScore(Long studentId) {
        List<ExerciseResult> results = exerciseResultRepository.findByStudentId(studentId);
        return results.stream().mapToInt(ExerciseResult::getScore).average().orElse(0);
    }
}
