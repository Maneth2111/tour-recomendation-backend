package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.tour.CreateTourImageRequest;
import com.example.Tour_Recommendation.dto.response.tour.TourImageResponse;

import java.util.List;

public interface TourImageService {

    ApiResponse<List<TourImageResponse>> getTourImages(Long tourId);

    ApiResponse<TourImageResponse> addTourImage(Long tourId, CreateTourImageRequest request);

    ApiResponse<Void> deleteTourImage(Long tourId, Long imageId);
}
