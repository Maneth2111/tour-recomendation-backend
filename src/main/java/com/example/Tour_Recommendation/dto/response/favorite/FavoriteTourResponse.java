package com.example.Tour_Recommendation.dto.response.favorite;

import com.example.Tour_Recommendation.model.entity.FavoriteTour;
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
public class FavoriteTourResponse {

    private Long id;

    @JsonProperty("tour_id")
    private Long tourId;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    private String title;

    private String description;

    private String location;

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

    @JsonProperty("is_favorite")
    private Boolean isFavorite;

    @JsonProperty("favorited_at")
    private Instant favoritedAt;

    public static FavoriteTourResponse from(FavoriteTour favoriteTour) {
        return FavoriteTourResponse.builder()
                .id(favoriteTour.getId())
                .tourId(favoriteTour.getTour().getId())
                .categoryId(favoriteTour.getTour().getCategory().getId())
                .categoryName(favoriteTour.getTour().getCategory().getName())
                .title(favoriteTour.getTour().getTitle())
                .description(favoriteTour.getTour().getDescription())
                .location(favoriteTour.getTour().getLocation())
                .durationHours(favoriteTour.getTour().getDurationHours())
                .price(favoriteTour.getTour().getPrice())
                .maxPeople(favoriteTour.getTour().getMaxPeople())
                .imageCover(favoriteTour.getTour().getImageCover())
                .avgRating(favoriteTour.getTour().getAvgRating())
                .isActive(favoriteTour.getTour().getIsActive())
                .isFavorite(true)
                .favoritedAt(favoriteTour.getCreatedAt())
                .build();
    }
}
