package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.review.CreateReviewRequest;
import com.example.Tour_Recommendation.dto.request.review.UpdateReviewRequest;
import com.example.Tour_Recommendation.dto.response.review.ReviewResponse;
import com.example.Tour_Recommendation.exception.review.ReviewNotFoundException;
import com.example.Tour_Recommendation.exception.tour.TourNotFoundException;
import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.entity.Review;
import com.example.Tour_Recommendation.model.entity.Tour;
import com.example.Tour_Recommendation.model.entity.User;
import com.example.Tour_Recommendation.repository.BookingRepository;
import com.example.Tour_Recommendation.repository.ReviewRepository;
import com.example.Tour_Recommendation.repository.TourRepository;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public ApiResponse<ReviewResponse> createReview(CustomUserDetails userDetails, CreateReviewRequest request) {
        User user = userDetails.getUser();
        Tour tour = tourRepository.findById(request.getTourId())
                .orElseThrow(() -> new TourNotFoundException(request.getTourId()));

        if (reviewRepository.existsByUserIdAndTourId(user.getId(), tour.getId())) {
            throw new RuntimeException("You have already reviewed this tour");
        }

        boolean hasCompletedBooking = bookingRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .anyMatch(booking ->
                        booking.getTour().getId().equals(tour.getId())
                                && booking.getStatus() == BookingStatus.COMPLETED
                );

        if (!hasCompletedBooking) {
            throw new RuntimeException("You can only review tours after completing a booking");
        }

        Review review = Review.builder()
                .user(user)
                .tour(tour)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        Review saved = reviewRepository.save(review);
        updateTourAvgRating(tour.getId());

        return ApiResponse.success("Review created successfully", ReviewResponse.from(saved));
    }

    @Override
    public ApiResponse<List<ReviewResponse>> getReviewsByTourId(Long tourId) {
        if (!tourRepository.existsById(tourId)) {
            throw new TourNotFoundException(tourId);
        }

        List<ReviewResponse> reviews = reviewRepository.findByTourIdOrderByCreatedAtDesc(tourId)
                .stream()
                .map(ReviewResponse::from)
                .toList();

        return ApiResponse.success("Reviews fetched successfully", reviews);
    }

    @Override
    public ApiResponse<List<ReviewResponse>> getMyReviews(CustomUserDetails userDetails) {
        List<ReviewResponse> reviews = reviewRepository
                .findByUserIdOrderByCreatedAtDesc(userDetails.getUser().getId())
                .stream()
                .map(ReviewResponse::from)
                .toList();

        return ApiResponse.success("Reviews fetched successfully", reviews);
    }

    @Override
    @Transactional
    public ApiResponse<ReviewResponse> updateReview(
            CustomUserDetails userDetails,
            Long id,
            UpdateReviewRequest request
    ) {
        Review review = reviewRepository.findByIdAndUserId(id, userDetails.getUser().getId())
                .orElseThrow(() -> new ReviewNotFoundException(id));

        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }
        if (request.getComment() != null) {
            review.setComment(request.getComment());
        }

        Review saved = reviewRepository.save(review);
        updateTourAvgRating(review.getTour().getId());

        return ApiResponse.success("Review updated successfully", ReviewResponse.from(saved));
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteReview(CustomUserDetails userDetails, Long id) {
        Review review = reviewRepository.findByIdAndUserId(id, userDetails.getUser().getId())
                .orElseThrow(() -> new ReviewNotFoundException(id));

        Long tourId = review.getTour().getId();
        reviewRepository.delete(review);
        updateTourAvgRating(tourId);

        return ApiResponse.success("Review deleted successfully", null);
    }

    private void updateTourAvgRating(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new TourNotFoundException(tourId));

        List<Review> reviews = reviewRepository.findByTourId(tourId);
        if (reviews.isEmpty()) {
            tour.setAvgRating(BigDecimal.ZERO);
        } else {
            double average = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0);
            tour.setAvgRating(BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP));
        }

        tourRepository.save(tour);
    }
}
