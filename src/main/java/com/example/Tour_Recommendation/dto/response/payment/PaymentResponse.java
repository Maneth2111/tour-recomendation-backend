package com.example.Tour_Recommendation.dto.response.payment;

import com.example.Tour_Recommendation.model.Enum.PaymentMethod;
import com.example.Tour_Recommendation.model.Enum.PaymentStatus;
import com.example.Tour_Recommendation.model.entity.Payment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;

    @JsonProperty("booking_id")
    private Long bookingId;

    @JsonProperty("booking_code")
    private String bookingCode;

    private BigDecimal amount;

    private PaymentMethod method;

    private PaymentStatus status;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("paid_at")
    private Instant paidAt;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking().getId())
                .bookingCode(payment.getBooking().getBookingCode())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
