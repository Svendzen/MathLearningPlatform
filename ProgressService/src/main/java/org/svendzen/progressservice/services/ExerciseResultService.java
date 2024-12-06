package org.svendzen.progressservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.progressservice.models.ExerciseResult;
import org.svendzen.progressservice.repositories.ExerciseResultRepository;

import java.util.List;

@Service
public class ExerciseResultService {

    @Autowired
    private ExerciseResultRepository exerciseResultRepository;

    // Save a new exercise result after a student completes an exercise
    public ExerciseResult saveExerciseResult(ExerciseResult exerciseResult) {
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

