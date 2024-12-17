ALTER TABLE progress_schema.student_progress
    ADD COLUMN division_exercises_completed BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN multiplication_exercises_completed BIGINT NOT NULL DEFAULT 0;
