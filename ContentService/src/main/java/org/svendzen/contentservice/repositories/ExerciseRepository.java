package org.svendzen.contentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.contentservice.models.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
