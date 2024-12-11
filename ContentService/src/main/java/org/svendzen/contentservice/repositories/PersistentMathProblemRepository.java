package org.svendzen.contentservice.repositories;

import org.svendzen.contentservice.models.PersistentMathProblem;
import org.svendzen.contentservice.models.MathTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for accessing and managing PersistentMathProblem entities in the database.
 */
public interface PersistentMathProblemRepository extends JpaRepository<PersistentMathProblem, Long> {

    /**
     * Finds all PersistentMathProblems for a given MathTopic.
     *
     * @param topic The MathTopic to filter by.
     * @return A list of PersistentMathProblems belonging to the specified MathTopic.
     */
    List<PersistentMathProblem> findByType(MathTopic topic);
}
