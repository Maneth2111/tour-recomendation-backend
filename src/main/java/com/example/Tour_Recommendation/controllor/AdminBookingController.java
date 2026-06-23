package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.booking.UpdateBookingStatusRequest;
import com.example.Tour_Recommendation.dto.response.booking.BookingResponse;
import com.example.Tour_Recommendation.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
@Tag(name = "Admin Booking")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminBookingController {

    private final BookingService bookingService;

    @GetMapping
    @Operation(summary = "Get all bookings")
    public ApiResponse<List<BookingResponse>> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update booking status")
    public ApiResponse<BookingResponse> updateBookingStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingStatusRequest request
    ) {
        return bookingService.updateBookingStatus(id, request.getStatus());
    }
}
