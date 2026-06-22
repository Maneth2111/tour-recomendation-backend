package com.example.Tour_Recommendation.security;

public record GoogleUserPayload(
        String googleId,
        String email,
        String name,
        String picture
) {
}
