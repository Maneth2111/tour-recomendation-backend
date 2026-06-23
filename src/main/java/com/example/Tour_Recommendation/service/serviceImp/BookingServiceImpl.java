package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.booking.CreateBookingRequest;
import com.example.Tour_Recommendation.dto.response.booking.BookingResponse;
import com.example.Tour_Recommendation.exception.booking.BookingNotFoundException;
import com.example.Tour_Recommendation.exception.tour.TourNotFoundException;
import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.entity.Booking;
import com.example.Tour_Recommendation.model.entity.Tour;
import com.example.Tour_Recommendation.model.entity.User;
import com.example.Tour_Recommendation.repository.BookingRepository;
import com.example.Tour_Recommendation.repository.TourRepository;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;

    @Override
    public ApiResponse<BookingResponse> createBooking(CustomUserDetails userDetails, CreateBookingRequest request) {
        User user = userDetails.getUser();
        Tour tour = tourRepository.findById(request.getTourId())
                .orElseThrow(() -> new TourNotFoundException(request.getTourId()));

        if (!Boolean.TRUE.equals(tour.getIsActive())) {
            throw new RuntimeException("Tour is not available for booking");
        }

        if (request.getPeopleCount() > tour.getMaxPeople()) {
            throw new RuntimeException("People count exceeds tour maximum of " + tour.getMaxPeople());
        }

        BigDecimal totalPrice = tour.getPrice().multiply(BigDecimal.valueOf(request.getPeopleCount()));

        Booking booking = Booking.builder()
                .bookingCode(generateBookingCode())
                .user(user)
                .tour(tour)
                .tourDate(request.getTourDate())
                .peopleCount(request.getPeopleCount())
                .totalPrice(totalPrice)
                .status(BookingStatus.PENDING)
                .build();

        Booking saved = bookingRepository.save(booking);
        return ApiResponse.success("Booking created successfully", BookingResponse.from(saved));
    }

    @Override
    public ApiResponse<List<BookingResponse>> getMyBookings(CustomUserDetails userDetails) {
        List<BookingResponse> bookings = bookingRepository
                .findByUserIdOrderByCreatedAtDesc(userDetails.getUser().getId())
                .stream()
                .map(BookingResponse::from)
                .toList();
        return ApiResponse.success("Bookings fetched successfully", bookings);
    }

    @Override
    public ApiResponse<BookingResponse> getMyBookingById(CustomUserDetails userDetails, Long id) {
        Booking booking = findUserBookingOrThrow(id, userDetails.getUser().getId());
        return ApiResponse.success("Booking fetched successfully", BookingResponse.from(booking));
    }

    @Override
    public ApiResponse<BookingResponse> cancelBooking(CustomUserDetails userDetails, Long id) {
        Booking booking = findUserBookingOrThrow(id, userDetails.getUser().getId());

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }
        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Completed booking cannot be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Booking saved = bookingRepository.save(booking);
        return ApiResponse.success("Booking cancelled successfully", BookingResponse.from(saved));
    }

    @Override
    public ApiResponse<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookings = bookingRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(BookingResponse::from)
                .toList();
        return ApiResponse.success("Bookings fetched successfully", bookings);
    }

    @Override
    public ApiResponse<BookingResponse> updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        booking.setStatus(status);
        Booking saved = bookingRepository.save(booking);
        return ApiResponse.success("Booking status updated successfully", BookingResponse.from(saved));
    }

    private Booking findUserBookingOrThrow(Long id, Long userId) {
        return bookingRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    private String generateBookingCode() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
