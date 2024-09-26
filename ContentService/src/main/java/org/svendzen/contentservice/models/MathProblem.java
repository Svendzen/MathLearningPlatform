package org.svendzen.contentservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MathProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private int answer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MathProblemType type;
}
