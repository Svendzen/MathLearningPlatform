-- Create schema for content service
CREATE SCHEMA IF NOT EXISTS content_schema;

-- Create table for GameMode
CREATE TABLE content_schema.game_mode (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    total_questions INT NOT NULL,
    max_points_per_question INT NOT NULL,
    seconds_per_question INT,
    is_score_based_on_time BOOLEAN NOT NULL,
    priority INT NOT NULL
);

-- Create table for GameMode supported topics
CREATE TABLE content_schema.game_mode_supported_topics (
    game_mode_id INT NOT NULL,
    math_topic VARCHAR(255) NOT NULL,
    PRIMARY KEY (game_mode_id, math_topic),
    CONSTRAINT fk_game_mode FOREIGN KEY (game_mode_id) REFERENCES content_schema.game_mode (id) ON DELETE CASCADE
);

-- Create table for PersistentMathProblem
CREATE TABLE content_schema.persistent_math_problem (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer INT NOT NULL,
    type VARCHAR(255) NOT NULL
);
