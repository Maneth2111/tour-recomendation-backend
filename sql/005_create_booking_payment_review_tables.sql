-- Bookings, payments, reviews, tour_images tables
-- psql -U postgres -d tour_recommendation_db -f sql/005_create_booking_payment_review_tables.sql

CREATE TABLE IF NOT EXISTS bookings (
    id            BIGSERIAL PRIMARY KEY,
    booking_code  VARCHAR(30) NOT NULL UNIQUE,
    user_id       BIGINT NOT NULL REFERENCES users(id),
    tour_id       BIGINT NOT NULL REFERENCES tours(id),
    tour_date     DATE NOT NULL,
    people_count  INTEGER NOT NULL,
    total_price   NUMERIC(10,2) NOT NULL,
    status        VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payments (
    id             BIGSERIAL PRIMARY KEY,
    booking_id     BIGINT NOT NULL UNIQUE REFERENCES bookings(id),
    amount         NUMERIC(10,2) NOT NULL,
    method         VARCHAR(30) NOT NULL,
    status         VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    transaction_id VARCHAR(255),
    paid_at        TIMESTAMP,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reviews (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL REFERENCES users(id),
    tour_id    BIGINT NOT NULL REFERENCES tours(id),
    rating     INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment    TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE (user_id, tour_id)
);

CREATE TABLE IF NOT EXISTS tour_images (
    id         BIGSERIAL PRIMARY KEY,
    tour_id    BIGINT NOT NULL REFERENCES tours(id),
    image_url  TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_bookings_user_id ON bookings (user_id);
CREATE INDEX IF NOT EXISTS idx_bookings_tour_id ON bookings (tour_id);
CREATE INDEX IF NOT EXISTS idx_bookings_status ON bookings (status);
CREATE INDEX IF NOT EXISTS idx_reviews_tour_id ON reviews (tour_id);
CREATE INDEX IF NOT EXISTS idx_tour_images_tour_id ON tour_images (tour_id);
