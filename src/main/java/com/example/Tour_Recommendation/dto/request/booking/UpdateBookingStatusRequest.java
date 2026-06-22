package com.example.Tour_Recommendation.dto.request.booking;

import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBookingStatusRequest {

    @NotNull
    private BookingStatus status;
}
