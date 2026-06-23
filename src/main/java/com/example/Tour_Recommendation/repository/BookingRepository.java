package com.example.Tour_Recommendation.repository;

import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(attributePaths = {"tour", "user"})
    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    @EntityGraph(attributePaths = {"tour", "user"})
    List<Booking> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"tour", "user"})
    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndTourIdAndStatus(Long userId, Long tourId, BookingStatus status);

    @EntityGraph(attributePaths = {"tour", "user"})
    Optional<Booking> findWithUserAndTourById(Long id);

    @EntityGraph(attributePaths = {"tour", "user"})
    Page<Booking> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = """
            SELECT CAST(b.created_at AS date) AS booking_date, COUNT(b.id) AS booking_count
            FROM bookings b
            WHERE b.created_at >= :startDate AND b.created_at < :endDate
            GROUP BY CAST(b.created_at AS date)
            ORDER BY booking_date
            """, nativeQuery = true)
    List<Object[]> countBookingsGroupedByDate(
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.status IN :statuses")
    BigDecimal sumTotalPriceByStatuses(@Param("statuses") Collection<BookingStatus> statuses);
}
