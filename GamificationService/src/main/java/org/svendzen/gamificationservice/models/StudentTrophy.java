package org.svendzen.gamificationservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.svendzen.enums.MathTopic;
import org.svendzen.enums.GameMode;
import org.svendzen.enums.TrophyLevel;

@Entity
@Data
@Table(
        name = "student_trophy",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"studentId", "mathTopic", "gameMode"})
        }
)
public class StudentTrophy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long studentId;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private MathTopic mathTopic; // Enum: ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION ...

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private GameMode gameMode; // Enum: CLASSIC, MULTIPLE_CHOICE

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private TrophyLevel trophyLevel; // Enum: BRONZE, SILVER, GOLD
}
