package org.svendzen.gamificationservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svendzen.gamificationservice.models.StudentTrophy;
import org.svendzen.gamificationservice.models.TrophyLevel;
import org.svendzen.gamificationservice.repositories.StudentTrophyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentTrophyService {

    @Autowired
    private StudentTrophyRepository studentTrophyRepository;

    public TrophyLevel awardTrophy(int scorePercentage) {
        if (scorePercentage >= 90) {
            return TrophyLevel.GOLD;
        } else if (scorePercentage >= 75) {
            return TrophyLevel.SILVER;
        } else if (scorePercentage >= 50) {
            return TrophyLevel.BRONZE;
        } else {
            return null;  // No trophy for scores below 50%
        }
    }

    public void handleExerciseResult(int scorePercentage, Long studentId, String mathTopic, String gameMode) {
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
            System.out.println("Awarded " + newTrophy + " trophy to student " + studentId + " for " + mathTopic + " in " + gameMode);

        } else if (existingTrophy.isPresent() && newTrophy != null) {
            // Check if the new trophy level is better than the current one
            StudentTrophy existing = existingTrophy.get();
            if (newTrophy.ordinal() > existing.getTrophyLevel().ordinal()) {
                // Upgrade the trophy
                existing.setTrophyLevel(newTrophy);
                studentTrophyRepository.save(existing);
                System.out.println("Upgraded to " + newTrophy + " trophy for student " + studentId + " for " + mathTopic + " in " + gameMode);
            } else {
                System.out.println("Student already has a better or equal trophy for " + mathTopic + " in " + gameMode);
            }
        } else {
            System.out.println("No trophy awarded. Score too low.");
        }
    }
    // Create: Add or update a trophy
    public StudentTrophy addOrUpdateTrophy(Long studentId, StudentTrophy trophy) {
        trophy.setStudentId(studentId);
        return studentTrophyRepository.save(trophy);
    }

    // Read: Get all trophies for a student
    public List<StudentTrophy> getTrophiesByStudentId(Long studentId) {
        return studentTrophyRepository.findByStudentId(studentId);
    }

    // Read: Get specific trophy for a student
    public Optional<StudentTrophy> getTrophyForExercise(Long studentId, String mathTopic, String gameMode) {
        return studentTrophyRepository.findByStudentIdAndMathTopicAndGameMode(studentId, mathTopic, gameMode);
    }

    // Delete: Remove a trophy (optional)
    public void deleteTrophy(Long trophyId) {
        studentTrophyRepository.deleteById(trophyId);
    }
}
