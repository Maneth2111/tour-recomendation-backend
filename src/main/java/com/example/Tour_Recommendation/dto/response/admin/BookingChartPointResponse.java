package com.example.Tour_Recommendation.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingChartPointResponse {

    private LocalDate date;

    @JsonProperty("booking_count")
    private long bookingCount;
}
