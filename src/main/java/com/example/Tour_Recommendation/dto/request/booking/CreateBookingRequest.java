package com.example.Tour_Recommendation.dto.request.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateBookingRequest {

    @NotNull
    @JsonProperty("tour_id")
    private Long tourId;

    @NotNull
    @Future
    @JsonProperty("tour_date")
    private LocalDate tourDate;

    @NotNull
    @Min(1)
    @JsonProperty("people_count")
    private Integer peopleCount;
}
