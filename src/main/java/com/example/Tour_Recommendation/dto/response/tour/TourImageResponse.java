package com.example.Tour_Recommendation.dto.response.tour;

import com.example.Tour_Recommendation.model.entity.TourImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourImageResponse {

    private Long id;

    @JsonProperty("tour_id")
    private Long tourId;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("created_at")
    private Instant createdAt;

    public static TourImageResponse from(TourImage tourImage) {
        return TourImageResponse.builder()
                .id(tourImage.getId())
                .tourId(tourImage.getTour().getId())
                .imageUrl(tourImage.getImageUrl())
                .createdAt(tourImage.getCreatedAt())
                .build();
    }
}
