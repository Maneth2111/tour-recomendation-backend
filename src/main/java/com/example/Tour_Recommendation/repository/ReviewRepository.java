package com.example.Tour_Recommendation.repository;

import com.example.Tour_Recommendation.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTourIdOrderByCreatedAtDesc(Long tourId);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Review> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndTourId(Long userId, Long tourId);

    List<Review> findByTourId(Long tourId);
}
