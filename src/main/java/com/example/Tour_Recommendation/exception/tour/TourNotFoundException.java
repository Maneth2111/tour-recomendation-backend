package com.example.Tour_Recommendation.exception.tour;
public class TourNotFoundException extends RuntimeException {
    public TourNotFoundException(Long id) {
        super("Tour not found with id: " + id);
    }
}