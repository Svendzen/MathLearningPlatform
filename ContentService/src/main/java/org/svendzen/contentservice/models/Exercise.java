package org.svendzen.contentservice.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/***
 * An exercise object determines the structure of the math exercise before its
 * attempted by a student.
***/

@Entity
@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Optional name for custom quizzes, auto-generated exercises may leave this blank
    private String name;  // e.g., "Homework chapter 3"

    @Column(nullable = false)
    private String mathTopic;  // e.g., "Addition"

    @Column(nullable = false)
    private String gameMode;  // e.g., "Rapid Fire", "Multiple Choice"

    @OneToMany(cascade = CascadeType.ALL)
    private List<MathProblem> mathProblems;  // Either manually added by teachers or auto-generated

    private int totalQuestions;  // Number of questions in the exercise

    private int maxPoints;  // Maximum points achievable in this exercise
}
