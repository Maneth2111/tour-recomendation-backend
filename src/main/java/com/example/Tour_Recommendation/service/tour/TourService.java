package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.tour.CreateTourRequest;
import com.example.Tour_Recommendation.dto.request.tour.UpdateTourRequest;
import com.example.Tour_Recommendation.dto.response.tour.TourResponse;

import java.util.List;

public interface TourService {

    ApiResponse<List<TourResponse>> getAllActiveTours();

    ApiResponse<TourResponse> getTourById(Long id);

    ApiResponse<List<TourResponse>> getToursByCategoryId(Long categoryId);

    ApiResponse<TourResponse> createTour(CreateTourRequest request);

    ApiResponse<TourResponse> updateTour(Long id, UpdateTourRequest request);

    ApiResponse<Void> deleteTour(Long id);
}