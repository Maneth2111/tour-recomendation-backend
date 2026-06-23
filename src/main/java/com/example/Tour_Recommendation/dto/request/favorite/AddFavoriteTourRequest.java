package com.example.Tour_Recommendation.dto.request.favorite;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddFavoriteTourRequest {

    @NotNull
    @JsonProperty("tour_id")
    private Long tourId;
}
