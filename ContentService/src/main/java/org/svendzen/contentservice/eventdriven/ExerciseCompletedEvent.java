package org.svendzen.contentservice.eventdriven;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseCompletedEvent {
    private Long studentId;
    private int scorePercentage;
    private String mathTopic;
    private String gameMode;
}
