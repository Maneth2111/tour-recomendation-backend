package com.example.Tour_Recommendation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GoogleAuthRequest {

    @NotBlank(message = "Google ID token is required")
    @JsonProperty("id_token")
    private String idToken;
}
