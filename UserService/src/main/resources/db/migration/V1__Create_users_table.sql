-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS user_schema;

-- Create the users table in the specified schema
CREATE TABLE user_schema.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);
