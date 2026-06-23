package com.example.Tour_Recommendation.dto.response.favorite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteStatusResponse {

    @JsonProperty("tour_id")
    private Long tourId;

    @JsonProperty("is_favorite")
    private boolean isFavorite;
}
