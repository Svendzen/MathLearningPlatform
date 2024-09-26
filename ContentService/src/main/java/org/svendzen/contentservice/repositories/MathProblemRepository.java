package org.svendzen.contentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.contentservice.models.MathProblem;

public interface MathProblemRepository extends JpaRepository<MathProblem, Long> {
}
