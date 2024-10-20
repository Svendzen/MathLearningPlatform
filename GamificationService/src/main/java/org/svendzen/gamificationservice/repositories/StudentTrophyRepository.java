package org.svendzen.gamificationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.gamificationservice.models.StudentTrophy;

public interface StudentTrophyRepository extends JpaRepository<StudentTrophy, Long> {
    StudentTrophy findByStudentIdAndMathTopicAndGameMode(Long studentId, String mathTopic, String gameMode);
}
