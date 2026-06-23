package com.example.Tour_Recommendation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String fullName;

    private String phone;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
