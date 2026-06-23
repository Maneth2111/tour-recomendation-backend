package com.example.Tour_Recommendation.dto.request.payment;

import com.example.Tour_Recommendation.model.Enum.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentRequest {

    @NotNull
    @JsonProperty("booking_id")
    private Long bookingId;

    @NotNull
    private PaymentMethod method;

    @JsonProperty("transaction_id")
    private String transactionId;
}
