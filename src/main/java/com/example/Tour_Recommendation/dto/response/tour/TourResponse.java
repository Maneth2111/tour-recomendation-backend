package com.example.Tour_Recommendation.dto.response.tour;

import com.example.Tour_Recommendation.model.entity.Tour;
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
public class TourResponse {

    private Long tourId;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    private String title;

    private String description;

    private String location;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @JsonProperty("duration_hours")
    private Integer durationHours;

    private BigDecimal price;

    @JsonProperty("max_people")
    private Integer maxPeople;

    @JsonProperty("image_cover")
    private String imageCover;

    @JsonProperty("avg_rating")
    private BigDecimal avgRating;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public static TourResponse from(Tour tour) {
        return TourResponse.builder()
                .tourId(tour.getId())
                .categoryId(tour.getCategory().getId())
                .categoryName(tour.getCategory().getName())
                .title(tour.getTitle())
                .description(tour.getDescription())
                .location(tour.getLocation())
                .latitude(tour.getLatitude())
                .longitude(tour.getLongitude())
                .durationHours(tour.getDurationHours())
                .price(tour.getPrice())
                .maxPeople(tour.getMaxPeople())
                .imageCover(tour.getImageCover())
                .avgRating(tour.getAvgRating())
                .isActive(tour.getIsActive())
                .createdAt(tour.getCreatedAt())
                .updatedAt(tour.getUpdatedAt())
                .build();
    }
}
