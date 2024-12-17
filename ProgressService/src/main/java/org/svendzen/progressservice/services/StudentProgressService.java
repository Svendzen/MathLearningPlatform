package org.svendzen.progressservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.progressservice.models.StudentProgress;
import org.svendzen.progressservice.repositories.StudentProgressRepository;

@Service
@Slf4j
public class StudentProgressService {

    @Autowired
    StudentProgressRepository studentProgressRepository;

    public StudentProgress getOrCreateStudentProgress(Long studentId) {
        return studentProgressRepository
                .findByStudentId(studentId)
                .orElseGet(() -> {
                    StudentProgress progress = new StudentProgress();
                    progress.setStudentId(studentId);
                    return progress;
                });
    }

    public void updateGeneralStats(StudentProgress progress, int correctAnswers, int totalQuestions) {
        progress.setTotalExercisesCompleted(progress.getTotalExercisesCompleted() + 1);
        progress.setTotalCorrectAnswers(progress.getTotalCorrectAnswers() + correctAnswers);
        progress.setTotalIncorrectAnswers(progress.getTotalIncorrectAnswers()
                + (totalQuestions - correctAnswers));
    }

    public void updateGameModeStats(StudentProgress progress, String gameMode) {
        if ("CLASSIC".equalsIgnoreCase(gameMode)) {
            progress.setClassicExercisesCompleted(progress.getClassicExercisesCompleted() + 1);
        } else if ("MULTIPLE_CHOICE".equalsIgnoreCase(gameMode)) {
            progress.setMultipleChoiceExercisesCompleted(progress.getMultipleChoiceExercisesCompleted() + 1);
        } else {
            log.warn("Unhandled game mode: {}", gameMode);
        }
    }

    public void updateTopicStats(StudentProgress progress, String mathTopic) {
        switch (mathTopic.toUpperCase()) {
            case "ADDITION":
                progress.setAdditionExercisesCompleted(progress.getAdditionExercisesCompleted() + 1);
                break;
            case "SUBTRACTION":
                progress.setSubtractionExercisesCompleted(progress.getSubtractionExercisesCompleted() + 1);
                break;
            case "MULTIPLICATION":
                progress.setMultiplicationExercisesCompleted(progress.getMultiplicationExercisesCompleted() + 1);
                break;
            case "DIVISION":
                progress.setDivisionExercisesCompleted(progress.getDivisionExercisesCompleted());
            default:
                log.warn("Unhandled math topic: {}", mathTopic);
        }
    }

    public void saveProgress(StudentProgress progress) {
        studentProgressRepository.save(progress);
        log.info("Updated StudentProgress: {}", progress);
    }

}
