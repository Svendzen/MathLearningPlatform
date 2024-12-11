package org.svendzen.contentservice.dtos;

import lombok.Data;
import org.svendzen.contentservice.models.MathTopic;

/***
 * DTO for requesting a specific type of MathProblem generation.
 */
@Data
public class ProblemRequest {
    private MathTopic type; // e.g., ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    private int count; // Number of problems to retrieve
    private boolean useDynamic; // Whether to generate dynamic problems
    private boolean usePersistent; // Whether to fetch persistent problems
}
