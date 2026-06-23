package com.example.Tour_Recommendation.dto.request.tour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTourImageRequest {

    @NotBlank
    @JsonProperty("image_url")
    private String imageUrl;
}
