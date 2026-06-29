-- Favorite tours (wishlist) table
-- psql -U postgres -d tour_recommendation_db -f sql/006_create_favorite_tours_table.sql

CREATE TABLE IF NOT EXISTS favorite_tours (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL REFERENCES users(id),
    tour_id    BIGINT NOT NULL REFERENCES tours(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, tour_id)
);

CREATE INDEX IF NOT EXISTS idx_favorite_tours_user_id ON favorite_tours (user_id);
CREATE INDEX IF NOT EXISTS idx_favorite_tours_tour_id ON favorite_tours (tour_id);
