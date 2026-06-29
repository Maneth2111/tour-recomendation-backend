-- Categories and tours (required before bookings/reviews/favorites)
-- psql -U postgres -d tour_recommendation_db -f sql/003_create_categories_and_tours_tables.sql

CREATE TABLE IF NOT EXISTS categories (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tours (
    id             BIGSERIAL PRIMARY KEY,
    category_id    BIGINT NOT NULL REFERENCES categories(id),
    title          VARCHAR(255) NOT NULL,
    description    TEXT,
    location       VARCHAR(255),
    latitude       NUMERIC(10, 7),
    longitude      NUMERIC(10, 7),
    duration_hours INTEGER,
    price          NUMERIC(10, 2),
    max_people     INTEGER,
    image_cover    TEXT,
    avg_rating     NUMERIC(3, 2) DEFAULT 0,
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_tours_category_id ON tours (category_id);
CREATE INDEX IF NOT EXISTS idx_tours_is_active ON tours (is_active);
