package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.review.CreateReviewRequest;
import com.example.Tour_Recommendation.dto.request.review.UpdateReviewRequest;
import com.example.Tour_Recommendation.dto.response.review.ReviewResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;

import java.util.List;

public interface ReviewService {

    ApiResponse<ReviewResponse> createReview(CustomUserDetails userDetails, CreateReviewRequest request);

    ApiResponse<List<ReviewResponse>> getReviewsByTourId(Long tourId);

    ApiResponse<List<ReviewResponse>> getMyReviews(CustomUserDetails userDetails);

    ApiResponse<ReviewResponse> updateReview(CustomUserDetails userDetails, Long id, UpdateReviewRequest request);

    ApiResponse<Void> deleteReview(CustomUserDetails userDetails, Long id);
}
