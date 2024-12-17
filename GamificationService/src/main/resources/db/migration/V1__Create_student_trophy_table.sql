CREATE SCHEMA IF NOT EXISTS gamification_schema;

CREATE TABLE gamification_schema.student_trophy (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    math_topic VARCHAR(255) NOT NULL,
    game_mode VARCHAR(255) NOT NULL,
    trophy_level VARCHAR(255) NOT NULL,
    UNIQUE (student_id, math_topic, game_mode)
);
