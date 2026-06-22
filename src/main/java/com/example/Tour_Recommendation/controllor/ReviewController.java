package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.review.CreateReviewRequest;
import com.example.Tour_Recommendation.dto.request.review.UpdateReviewRequest;
import com.example.Tour_Recommendation.dto.response.review.ReviewResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/tour/{tourId}")
    @Operation(summary = "Get reviews by tour ID")
    public ApiResponse<List<ReviewResponse>> getReviewsByTourId(@PathVariable Long tourId) {
        return reviewService.getReviewsByTourId(tourId);
    }

    @PostMapping
    @Operation(summary = "Create review")
    @SecurityRequirement(name = "Bearer Authentication")
    public ApiResponse<ReviewResponse> createReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateReviewRequest request
    ) {
        return reviewService.createReview(userDetails, request);
    }

    @GetMapping("/me")
    @Operation(summary = "Get my reviews")
    @SecurityRequirement(name = "Bearer Authentication")
    public ApiResponse<List<ReviewResponse>> getMyReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return reviewService.getMyReviews(userDetails);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update my review")
    @SecurityRequirement(name = "Bearer Authentication")
    public ApiResponse<ReviewResponse> updateReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody UpdateReviewRequest request
    ) {
        return reviewService.updateReview(userDetails, id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete my review")
    @SecurityRequirement(name = "Bearer Authentication")
    public ApiResponse<Void> deleteReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        return reviewService.deleteReview(userDetails, id);
    }
}
