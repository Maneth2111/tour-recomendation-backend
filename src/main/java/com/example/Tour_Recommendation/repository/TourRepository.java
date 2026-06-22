package com.example.Tour_Recommendation.repository;

import com.example.Tour_Recommendation.model.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    boolean existsByTitle(String title);

    List<Tour> findByCategoryId(Long categoryId);

    List<Tour> findByCategoryIdAndIsActiveTrue(Long categoryId);

    Page<Tour> findByIsActiveTrue(Pageable pageable);
}
