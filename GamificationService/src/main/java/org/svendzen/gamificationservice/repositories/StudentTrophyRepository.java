package org.svendzen.gamificationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.enums.GameMode;
import org.svendzen.enums.MathTopic;
import org.svendzen.gamificationservice.models.StudentTrophy;

import java.util.List;
import java.util.Optional;

public interface StudentTrophyRepository extends JpaRepository<StudentTrophy, Long> {
    List<StudentTrophy> findByStudentId(Long studentId);
    Optional<StudentTrophy> findByStudentIdAndMathTopicAndGameMode(Long studentId, MathTopic mathTopic, GameMode gameMode);
}
