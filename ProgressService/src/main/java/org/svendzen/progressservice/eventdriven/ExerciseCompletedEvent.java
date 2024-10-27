package org.svendzen.progressservice.eventdriven;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseCompletedEvent {
    private Long studentId;
    private String mathTopic;
    private String gameMode;
    private int scorePercentage;
}
