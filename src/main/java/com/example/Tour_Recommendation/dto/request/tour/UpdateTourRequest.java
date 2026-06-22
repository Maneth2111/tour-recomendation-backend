package com.example.Tour_Recommendation.dto.request.tour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateTourRequest {

    @JsonProperty("category_id")
    private Long categoryId;

    @Size(max = 255)
    private String title;

    private String description;

    @Size(max = 255)
    private String location;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @Min(1)
    @JsonProperty("duration_hours")
    private Integer durationHours;

    @DecimalMin("0.0")
    private BigDecimal price;

    @Min(1)
    @JsonProperty("max_people")
    private Integer maxPeople;

    @JsonProperty("image_cover")
    private String imageCover;

    @JsonProperty("is_active")
    private Boolean isActive;
}