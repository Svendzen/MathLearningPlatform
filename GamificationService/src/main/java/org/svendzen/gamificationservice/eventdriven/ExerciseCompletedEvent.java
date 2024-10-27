package org.svendzen.gamificationservice.eventdriven;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseCompletedEvent {
    private Long studentId;
    private int scorePercentage;
    private String mathTopic;
    private String gameMode;
}
