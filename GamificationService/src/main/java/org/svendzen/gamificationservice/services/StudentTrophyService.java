package org.svendzen.gamificationservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.enums.GameMode;
import org.svendzen.enums.MathTopic;
import org.svendzen.gamificationservice.models.StudentTrophy;
import org.svendzen.gamificationservice.repositories.StudentTrophyRepository;
import org.svendzen.enums.TrophyLevel;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentTrophyService {

    @Autowired
    private StudentTrophyRepository studentTrophyRepository;

    public TrophyLevel awardTrophy(int scorePercentage) {
        if (scorePercentage >= 85) {
            return TrophyLevel.GOLD;
        } else if (scorePercentage >= 70) {
            return TrophyLevel.SILVER;
        } else if (scorePercentage >= 50) {
            return TrophyLevel.BRONZE;
        } else {
            return TrophyLevel.NONE;  // No trophy for scores below 50%
        }
    }

    public void handleExerciseResult(int scorePercentage, Long studentId, MathTopic mathTopic, GameMode gameMode) {
        TrophyLevel newTrophy = awardTrophy(scorePercentage);

        // Check if student already has a trophy for this mathTopic and gameMode
        Optional<StudentTrophy> existingTrophy = studentTrophyRepository.findByStudentIdAndMathTopicAndGameMode(studentId, mathTopic, gameMode);

        if (existingTrophy.isEmpty() && newTrophy != null) {
            // No existing trophy, award the new trophy
            StudentTrophy studentTrophy = new StudentTrophy();
            studentTrophy.setStudentId(studentId);
            studentTrophy.setMathTopic(mathTopic);
            studentTrophy.setGameMode(gameMode);
            studentTrophy.setTrophyLevel(newTrophy);

            studentTrophyRepository.save(studentTrophy);
            log.info("Awarded new {} trophy to student {}", newTrophy, studentId);
        } else if (existingTrophy.isPresent() && newTrophy != null) {
            StudentTrophy existing = existingTrophy.get();
            if (newTrophy.ordinal() > existing.getTrophyLevel().ordinal()) {
                existing.setTrophyLevel(newTrophy);
                studentTrophyRepository.save(existing);
                log.info("Upgraded trophy to {} for student {}", newTrophy, studentId);
            } else {
                log.info("No trophy upgrade needed for student {}", studentId);
            }
        } else {
            log.info("No trophy awarded. Score too low for student {}", studentId);
        }
    }

    // Create: Add or update a trophy
    public StudentTrophy saveOrUpdateTrophy(Long studentId, StudentTrophy trophy) {
        trophy.setStudentId(studentId);
        return studentTrophyRepository.save(trophy);
    }

    // Read: Get all trophies for a student
    public List<StudentTrophy> getTrophiesByStudentId(Long studentId) {
        return studentTrophyRepository.findByStudentId(studentId);
    }

    // Read: Get specific trophy for a student
    public Optional<StudentTrophy> getTrophyForExercise(Long studentId, MathTopic mathTopic, GameMode gameMode) {
        return studentTrophyRepository.findByStudentIdAndMathTopicAndGameMode(studentId, mathTopic, gameMode);
    }

    // Delete: Remove a trophy (optional)
    public void deleteTrophy(Long trophyId) {
        studentTrophyRepository.deleteById(trophyId);
    }
}
