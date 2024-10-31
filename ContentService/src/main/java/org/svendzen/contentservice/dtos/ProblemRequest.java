package org.svendzen.contentservice.dtos;

import lombok.Data;
import org.svendzen.contentservice.models.MathProblemType;

/***
 * DTO for requesting a specific type of MathProblem generation.
 */
@Data
public class ProblemRequest {
    private MathProblemType type; // e.g., ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
}
