package com.example.Tour_Recommendation.exception.tour;

public class TourImageNotFoundException extends RuntimeException {

    public TourImageNotFoundException(Long id) {
        super("Tour image not found with id: " + id);
    }
}
