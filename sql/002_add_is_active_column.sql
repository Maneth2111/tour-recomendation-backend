-- Safe migration: add is_active to existing users table
-- Run if Hibernate failed with: column "is_active" contains null values
-- psql -U postgres -d tour_recommendation_db -f sql/002_add_is_active_column.sql

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'users'
          AND column_name = 'is_active'
    ) THEN
        ALTER TABLE users
            ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;
    ELSE
        UPDATE users
        SET is_active = TRUE
        WHERE is_active IS NULL;

        ALTER TABLE users
            ALTER COLUMN is_active SET DEFAULT TRUE;

        ALTER TABLE users
            ALTER COLUMN is_active SET NOT NULL;
    END IF;
END $$;

CREATE INDEX IF NOT EXISTS idx_users_is_active ON users (is_active);
