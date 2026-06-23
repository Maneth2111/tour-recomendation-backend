package com.example.Tour_Recommendation.exception.favorite;

public class FavoriteTourNotFoundException extends RuntimeException {

    public FavoriteTourNotFoundException(Long tourId) {
        super("Favorite tour not found for tour id: " + tourId);
    }
}
