package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.payment.CreatePaymentRequest;
import com.example.Tour_Recommendation.dto.response.payment.PaymentResponse;
import com.example.Tour_Recommendation.exception.booking.BookingNotFoundException;
import com.example.Tour_Recommendation.exception.payment.PaymentNotFoundException;
import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.Enum.PaymentStatus;
import com.example.Tour_Recommendation.model.entity.Booking;
import com.example.Tour_Recommendation.model.entity.Payment;
import com.example.Tour_Recommendation.repository.BookingRepository;
import com.example.Tour_Recommendation.repository.PaymentRepository;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public ApiResponse<PaymentResponse> createPayment(CustomUserDetails userDetails, CreatePaymentRequest request) {
        Booking booking = bookingRepository.findByIdAndUserId(
                        request.getBookingId(),
                        userDetails.getUser().getId()
                )
                .orElseThrow(() -> new BookingNotFoundException(request.getBookingId()));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Only pending bookings can be paid");
        }

        if (paymentRepository.existsByBookingId(booking.getId())) {
            throw new RuntimeException("Payment already exists for this booking");
        }

        String transactionId = request.getTransactionId();
        if (transactionId == null || transactionId.isBlank()) {
            transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        }

        Payment payment = Payment.builder()
                .booking(booking)
                .amount(booking.getTotalPrice())
                .method(request.getMethod())
                .status(PaymentStatus.PAID)
                .transactionId(transactionId)
                .paidAt(Instant.now())
                .build();

        Payment saved = paymentRepository.save(payment);

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return ApiResponse.success("Payment completed successfully", PaymentResponse.from(saved));
    }

    @Override
    public ApiResponse<PaymentResponse> getPaymentByBookingId(CustomUserDetails userDetails, Long bookingId) {
        bookingRepository.findByIdAndUserId(bookingId, userDetails.getUser().getId())
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for booking id: " + bookingId));

        return ApiResponse.success("Payment fetched successfully", PaymentResponse.from(payment));
    }
}
