package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.payment.CreatePaymentRequest;
import com.example.Tour_Recommendation.dto.response.payment.PaymentResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;

public interface PaymentService {

    ApiResponse<PaymentResponse> createPayment(CustomUserDetails userDetails, CreatePaymentRequest request);

    ApiResponse<PaymentResponse> getPaymentByBookingId(CustomUserDetails userDetails, Long bookingId);
}
