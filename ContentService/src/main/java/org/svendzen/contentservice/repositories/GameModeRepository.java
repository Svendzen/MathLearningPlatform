package org.svendzen.contentservice.repositories;


import org.svendzen.contentservice.models.GameMode;
import org.svendzen.contentservice.models.MathTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for accessing and managing GameMode entities in the database.
 */
public interface GameModeRepository extends JpaRepository<GameMode, Long> {

    /**
     * Finds all game modes that support a given MathTopic, ordered by priority.
     *
     * @param topic The MathTopic to filter by.
     * @return A list of GameModes supporting the given MathTopic, sorted by priority.
     */
    List<GameMode> findBySupportedMathTopicsContainingOrderByPriorityAsc(MathTopic topic);
}
