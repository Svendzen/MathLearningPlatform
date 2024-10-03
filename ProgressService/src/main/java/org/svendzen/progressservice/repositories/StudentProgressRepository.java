package org.svendzen.progressservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.progressservice.models.StudentProgress;

import java.util.Optional;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, Long> {
    Optional<StudentProgress> findByStudentId(Long studentId);
}
