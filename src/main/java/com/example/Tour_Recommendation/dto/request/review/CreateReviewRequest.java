package com.example.Tour_Recommendation.dto.request.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReviewRequest {

    @NotNull
    @JsonProperty("tour_id")
    private Long tourId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;
}
