package com.example.Tour_Recommendation.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateReviewRequest {

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;
}
