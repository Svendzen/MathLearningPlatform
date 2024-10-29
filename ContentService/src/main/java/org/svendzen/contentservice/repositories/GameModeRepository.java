package org.svendzen.contentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svendzen.contentservice.models.GameMode;

public interface GameModeRepository extends JpaRepository<GameMode, Long> {
}
