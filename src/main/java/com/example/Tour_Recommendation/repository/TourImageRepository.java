package com.example.Tour_Recommendation.repository;

import com.example.Tour_Recommendation.model.entity.TourImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourImageRepository extends JpaRepository<TourImage, Long> {

    List<TourImage> findByTourIdOrderByCreatedAtAsc(Long tourId);

    Optional<TourImage> findByIdAndTourId(Long id, Long tourId);
}
