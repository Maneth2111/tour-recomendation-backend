package com.example.Tour_Recommendation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserStatusRequest {

    @NotNull
    @JsonProperty("is_active")
    private Boolean isActive;
}
