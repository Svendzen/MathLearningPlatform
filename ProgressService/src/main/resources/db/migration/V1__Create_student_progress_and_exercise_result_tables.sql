-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS progress_schema;

-- Create table for StudentProgress
CREATE TABLE progress_schema.student_progress (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    total_exercises_completed BIGINT NOT NULL DEFAULT 0,
    total_correct_answers BIGINT NOT NULL DEFAULT 0,
    total_incorrect_answers BIGINT NOT NULL DEFAULT 0,
    classic_exercises_completed BIGINT NOT NULL DEFAULT 0,
    multiple_choice_exercises_completed BIGINT NOT NULL DEFAULT 0,
    addition_exercises_completed BIGINT NOT NULL DEFAULT 0,
    subtraction_exercises_completed BIGINT NOT NULL DEFAULT 0
);

-- Create table for ExerciseResult
CREATE TABLE progress_schema.exercise_result (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    math_topic VARCHAR(255) NOT NULL,
    game_mode VARCHAR(255) NOT NULL,
    total_questions INTEGER NOT NULL,
    correct_answers INTEGER NOT NULL,
    score INTEGER NOT NULL,
    score_percentage INTEGER NOT NULL,
    completion_time BIGINT NOT NULL,
    completion_date TIMESTAMP NOT NULL
);

-- Add indices for performance on student_id
CREATE INDEX idx_student_progress_student_id ON progress_schema.student_progress(student_id);
CREATE INDEX idx_exercise_result_student_id ON progress_schema.exercise_result(student_id);
