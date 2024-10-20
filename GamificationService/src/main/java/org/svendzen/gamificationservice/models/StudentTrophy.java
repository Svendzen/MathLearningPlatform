package org.svendzen.gamificationservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class StudentTrophy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private long studentId;

    @NotBlank
    @Column(nullable = false)
    private String mathTopic; // e.g ADDITION, DIVISION

    @NotBlank
    @Column(nullable = false)
    private String gameMode;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(nullable = false)
    private TrophyLevel trophyLevel;
}
