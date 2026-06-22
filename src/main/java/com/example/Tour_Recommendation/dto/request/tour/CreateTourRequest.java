package com.example.Tour_Recommendation.dto.request.tour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTourRequest {

    @NotNull
    @JsonProperty("category_id")
    private Long categoryId;

    @NotBlank
    @Size(max = 255)
    private String title;

    private String description;

    @Size(max = 255)
    private String location;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private BigDecimal latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
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
}
