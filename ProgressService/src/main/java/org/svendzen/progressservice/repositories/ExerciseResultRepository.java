package org.svendzen.progressservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.progressservice.models.ExerciseResult;

import java.util.List;

public interface ExerciseResultRepository extends JpaRepository<ExerciseResult, Long> {
    List<ExerciseResult> findByStudentId(Long studentId);
}
