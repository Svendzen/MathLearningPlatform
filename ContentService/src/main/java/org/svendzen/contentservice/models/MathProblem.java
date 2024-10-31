    package org.svendzen.contentservice.models;

    import jakarta.persistence.*;
    import lombok.Data;

    // Base class for dynamically generated MathProblems
    @MappedSuperclass
    @Data
    public class MathProblem {
        @Column(nullable = false)
        private String question;

        @Column(nullable = false)
        private int answer;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private MathProblemType type;
    }

