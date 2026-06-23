package com.example.Tour_Recommendation.repository;

import com.example.Tour_Recommendation.model.entity.FavoriteTour;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteTourRepository extends JpaRepository<FavoriteTour, Long> {

    @EntityGraph(attributePaths = {"tour", "tour.category"})
    List<FavoriteTour> findByUserIdOrderByCreatedAtDesc(Long userId);

    @EntityGraph(attributePaths = {"tour", "tour.category"})
    Optional<FavoriteTour> findByUserIdAndTourId(Long userId, Long tourId);

    boolean existsByUserIdAndTourId(Long userId, Long tourId);

    void deleteByUserIdAndTourId(Long userId, Long tourId);
}
