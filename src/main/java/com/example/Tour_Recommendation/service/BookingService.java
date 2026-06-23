package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.booking.CreateBookingRequest;
import com.example.Tour_Recommendation.dto.response.booking.BookingResponse;
import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.security.CustomUserDetails;

import java.util.List;

public interface BookingService {

    ApiResponse<BookingResponse> createBooking(CustomUserDetails userDetails, CreateBookingRequest request);

    ApiResponse<List<BookingResponse>> getMyBookings(CustomUserDetails userDetails);

    ApiResponse<BookingResponse> getMyBookingById(CustomUserDetails userDetails, Long id);

    ApiResponse<BookingResponse> cancelBooking(CustomUserDetails userDetails, Long id);

    ApiResponse<List<BookingResponse>> getAllBookings();

    ApiResponse<BookingResponse> updateBookingStatus(Long id, BookingStatus status);
}
