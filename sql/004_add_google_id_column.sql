-- Add google_id for Google Sign-In users
-- psql -U postgres -d tour_recommendation_db -f sql/004_add_google_id_column.sql

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS google_id VARCHAR(255);

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_google_id ON users (google_id)
    WHERE google_id IS NOT NULL;

-- Allow password to be null for Google-only accounts
ALTER TABLE users
    ALTER COLUMN password DROP NOT NULL;
