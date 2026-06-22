package com.example.Tour_Recommendation.repository;

import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Booking> findAllByOrderByCreatedAtDesc();

    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndTourIdAndStatus(Long userId, Long tourId, BookingStatus status);
}
