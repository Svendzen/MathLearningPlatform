-- Update game mode name to ensure consistency
UPDATE content_schema.game_mode
SET name = 'Classic'
WHERE name = 'Classic Mode';