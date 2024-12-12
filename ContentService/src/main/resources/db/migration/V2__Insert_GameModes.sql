-- Insert predefined game modes into the GameMode table
INSERT INTO content_schema.game_mode (name, description, total_questions, max_points_per_question, seconds_per_question, is_score_based_on_time, priority)
VALUES
    ('Classic Mode', 'Answer as quickly as possible to maximize your score. Points decrease the longer you take.', 10, 100, 10, TRUE, 1),
    ('Multiple Choice', 'Choose the correct answer from four options. No time limit.', 10, 100, 0, FALSE, 2);

-- Map supported MathTopics to the game modes
INSERT INTO content_schema.game_mode_supported_topics (game_mode_id, math_topic)
VALUES
    -- Classic Mode supports all topics
    (1, 'ADDITION'),
    (1, 'SUBTRACTION'),
    (1, 'MULTIPLICATION'),
    (1, 'DIVISION'),
    -- Multiple Choice supports all topics
    (2, 'ADDITION'),
    (2, 'SUBTRACTION'),
    (2, 'MULTIPLICATION'),
    (2, 'DIVISION');
