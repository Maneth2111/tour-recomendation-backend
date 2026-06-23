package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.booking.CreateBookingRequest;
import com.example.Tour_Recommendation.dto.response.booking.BookingResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking")
@SecurityRequirement(name = "Bearer Authentication")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create booking")
    public ApiResponse<BookingResponse> createBooking(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateBookingRequest request
    ) {
        return bookingService.createBooking(userDetails, request);
    }

    @GetMapping
    @Operation(summary = "Get my bookings")
    public ApiResponse<List<BookingResponse>> getMyBookings(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return bookingService.getMyBookings(userDetails);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get my booking by ID")
    public ApiResponse<BookingResponse> getMyBookingById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        return bookingService.getMyBookingById(userDetails, id);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel my booking")
    public ApiResponse<BookingResponse> cancelBooking(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        return bookingService.cancelBooking(userDetails, id);
    }
}
