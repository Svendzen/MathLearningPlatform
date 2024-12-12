package org.svendzen.contentservice.dtos;

import lombok.Data;
import org.svendzen.contentservice.models.MathTopic;

/***
 * DTO for requesting a specific GameMode with a specific math topic for the Math Problems
 */
@Data
public class GameModeRequest {
    private Long gameModeId;    // id of the game mode being requested
    private MathTopic topic;    // MathTopic (e.g.: ADDITION) for the math problems being displayed
}

