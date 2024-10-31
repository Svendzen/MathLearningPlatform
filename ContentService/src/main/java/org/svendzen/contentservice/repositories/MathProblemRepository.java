package org.svendzen.contentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.contentservice.models.MathProblemType;
import org.svendzen.contentservice.models.PersistentMathProblem;

import java.util.List;

public interface MathProblemRepository extends JpaRepository<PersistentMathProblem, Long> {
    List<PersistentMathProblem> findByType(MathProblemType type);
}
